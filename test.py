from urllib2 import *
def linkfinder(url, depth):
    begin = 0
    end = 0
    linklist = []
    linklist2 = []
    if depth == 0 :
        return linklist

    opener = urlopen(url)
    html = str(opener.read())
    while begin != -1 :
        begin = html.find("href=", end)
        end = html.find('"', begin+6)
        link = html[begin+6 : end]
        if (link[-1] == "/") and ("http://" not in link) and ("https://" not in link) or (url[url.find("www")+3:]in link):
            linklist2.append(link)
        elif (".jpg" in link) or (".mp3" in link) or (".pdf" in link):
            linklist.append(link)

    for item in linklist2:
        if ("http://" in item) or ("https://" in item):
            linklist = linklist + linkfinder(item, depth - 1)
        else :
            linklist = linklist + linkfinder(url + item, depth - 1)
    return linklist

site = str(raw_input("enter your site addres :" ))
deep = int(input("enter the depth of inner links (an intiger) : "))
new = linkfinder(site , deep )

for item in new :
    print item

