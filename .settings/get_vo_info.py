#!/usr/bin/python
############################################################
#
# Python client to download the VO XML file, 
# parse it and store the output into the HLRmon DB.
# Giuseppe Misurelli
# giuseppe.misurelli@cnaf.infn.it
# Enrico Fattibene
# enrico.fattibene@cnaf.infn.it
# Marco Bencivenni
# marco.bencivenni@cnaf.infn.it
# Last modification: July 10th, 2011 
#
############################################################

import sys, pycurl, StringIO, MySQLdb
from xml.dom import minidom

import time

now = time.strftime("%Y-%m-%d %H:%M:%S")

#
#PARSING CONF FILE
#


####inzio benci commenti######
#dbconf='../db_conn.conf' #temporary solution
#db=open(dbconf,'r')
#dbL=db.readlines()
#db.close()
#string_to_exec_db = ""
#for l in dbL:
#    if l[0]=='#' or l.find('=') <= 0: continue
#    #print "interpreto: %s"%l.rstrip('\n')
#    string_to_exec_db += l #exec l.rstrip('\n')
#exec string_to_exec_db

#fconf='./get_vo_disciplines.conf' #temporary solution
#f=open(fconf,'r')
#L=f.readlines()
#f.close()
#string_to_exec = ""
#for l in L:
#    if l[0]=='#' or l.find('=') <= 0: continue
#    #print "interpreto: %s"%l.rstrip('\n')
#    string_to_exec += l #exec l.rstrip('\n')

#exec string_to_exec # In this way it's possible to write configuration directives in more than one line. Peter.

###fine benci commenti####

# Main function that execute all the functions defined
def main():

    # CIC portal endpoint to query for the xml file
    global cic_ep 
    global result
    result = StringIO.StringIO()
 
    cic_ep = "http://operations-portal.in2p3.fr/xml/voIDCard/public/all/true"

    # Functions that will be invoked and executed
    curlDownload()
    xmlParsing()
    mysqlSaving()

# Performs curl download and store result into the global IO string variable
def curlDownload():  
  
    c = pycurl.Curl()
    c.setopt(c.URL, cic_ep)
    #c.setopt(c.VERBOSE, 1) # Need verbosity?
    c.setopt(c.SSLCERT, "/etc/grid-security/hostcert.pem")
    c.setopt(c.SSLKEY, "/etc/grid-security/hostkey.pem")
    c.setopt(c.CAPATH, "/etc/grid-security/certificates/")
    c.setopt(c.WRITEFUNCTION, result.write)
    c.perform()
    c.close()
    
    return result

# Performs xml parsing from the xml_doc string and save result into a dictionary handler
def xmlParsing():

    xml_doc = result.getvalue()
    
    try:
        doc = minidom.parseString(xml_doc)
        
    except minidom.DOMException, e:
        print e


    idcard = doc.getElementsByTagName("IDCard") 
    
    # Initializing the handler dictionary
    handler_dict  = {}
    
    # Iterating over the discipline tags and creating the mysql handler dictionary
    for ds in idcard:
#	print ds.getAttributeNode("Name").value
        if ds.getAttributeNode("Name"):
            attrs_name = ds.attributes["Name"]
            # All the DISCIPLINE's tag children
            vos = ds.childNodes
            # Initializing the VO list
            vo_list = []
	    voms_list = []
            for v in vos:
		if v.nodeName == "Discipline":
                    attrs_voname = v.childNodes[0].nodeValue
#		    print attrs_voname
                    vo_list.append(str(attrs_voname))
		if v.nodeName == "EnrollmentUrl":
	            if v.childNodes:
                        attrs_enroll = v.childNodes[0].nodeValue
#                        print attrs_enroll
                        vo_list.append(str(attrs_enroll))
		    else: 
			vo_list.append("")
#               if v.nodeName == "EnrollmentUrl":
#	                if v.childNodes:
#      			url = v.childNodes[0].nodeValue
#	#		print url
#			slashparts = url.split('/')
#			print 'slashparts = %s' % slashparts
#			list = [slashparts[2], 'voms']
#			vomsserver = '/'.join(list)
#			vo_list.append(str(vomsserver))
		if v.nodeName == "gLiteConf":
		    for voms0 in v.childNodes:
		        if voms0.nodeName == "VOMSServers":
		             for voms in voms0.childNodes:
			         if voms.nodeName == "VOMS_Server":
				   n=1
				   for n in range(1,1):
			             for vomsElement in voms.childNodes:
				         if vomsElement.nodeName == "hostname":
					      n =+ 1

#                    if v.childNodes:
 	                            	      attrs_enroll = vomsElement.childNodes[0].nodeValue
#                                              print attrs_enroll
                                              voms_list.append(str(attrs_enroll))
					      vo_list.append(voms_list)
					      hostport = [attrs_enroll , '8443']
					      vomshostport = ':'.join(hostport)
					      list = [vomshostport , 'voms' , ds.getAttributeNode("Name").value]
                       		              vomsserver = '/'.join(list)
					      print ds.getAttributeNode("Name").value
					      print vomsserver
                       			      vo_list.append(str(vomsserver))
#                    else:
#                        vo_list.append("")
                                
        handler_dict[str(attrs_name.value)] = vo_list

#    print handler_dict
    return handler_dict
        
# Insert and updates handler entries into the HLRmon database
def mysqlSaving():
    callback = xmlParsing()
    
    # Open connection to the rocrep_hlr_dev db (JUST FOR TEST - THEN WILL BE PARAMETRZIZED)
    try:
        conn = MySQLdb.connect(host = dbprm['dbhost'], user = dbprm['dbuser'], passwd = dbprm['dbpwd'], db = dbprm['dbname'])
        # Required cursor to deal with the slq statements to insert values into the fullscale table
        cursor = conn.cursor()
        # Generating all the SQL query for the INSERT operations
        for disciplines in callback.keys():
            for vos in callback[disciplines]:
                sql = "INSERT into vo_disciplines (vo, long_discipline, discipline, insert_time) VALUES"
                sql += "('" + vos + "', '"
#                sql += disciplines + "', '"

		sql += hostname + "BENCI', '"
		sql += short_discipline[disciplines] + "', '"
		sql += now + "') ON DUPLICATE KEY UPDATE id=LAST_INSERT_ID(id);"
#                cursor.execute(sql)
        cursor.close()
        conn.close()
    except MySQLdb.Error, e:
        print "Error %d: %s" % (e.args[0], e.args[1])
        sys.exit(1)
         
if __name__=="__main__":
    main()
