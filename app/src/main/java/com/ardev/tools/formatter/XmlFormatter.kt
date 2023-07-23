package com.ardev.tools.formatter

import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.io.StringReader

object XmlFormatter {
    fun format(xml: String): String {
        try {
            val xpp: XmlPullParser = XmlPullParserFactory.newInstance().newPullParser()
            val reader = StringReader(xml)
            xpp.setInput(reader)

            var currentTag = xpp.eventType
            while (currentTag != XmlPullParser.START_DOCUMENT) {
                currentTag = xpp.next()
            }
            val tags = ArrayList<String>()
            val sb = StringBuilder()
            var indent = ""
            var isEmpty = false
            currentTag = xpp.next()
            while (currentTag != XmlPullParser.END_DOCUMENT) {
                when (currentTag) {
                    XmlPullParser.END_TAG -> {
                        indent = indent.substring(1)
                        var lastItem = tags.size - 1
                        if (lastItem < 0) lastItem = 0
                        if (!isEmpty)
                            sb.append(indent).append("</").append(tags[lastItem]).append(">").append("\n")
                        tags.removeAt(lastItem)
                        isEmpty = false
                    }
                    XmlPullParser.START_TAG -> {
                        val tagName = xpp.name
                        tags.add(tagName)
                        sb.append(indent).append("<").append(tagName)
                        indent += "    "
                        val attrCount = xpp.attributeCount
                        if (attrCount > 0) sb.append("\n")
                        for (i in 0 until attrCount) {
                            val attrName = xpp.getAttributeName(i)
                            val attrValue = xpp.getAttributeValue(i)
                            sb.append(indent).append(attrName).append("=\"").append(attrValue).append("\"").append(if (i == (attrCount - 1)) "" else "\n")
                        }
                        if (xpp.isEmptyElementTag) {
                            sb.append("/>" + "\n")
                            isEmpty = true
                        } else {
                            sb.append(">" + "\n")
                            isEmpty = false
                        }
                    }
                }
                currentTag = xpp.next()
            }

            reader.close()
            return sb.toString()
        } catch (e: Exception) {
            return xml
        }
    }
}