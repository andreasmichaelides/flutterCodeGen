package codegen.tools

import org.xmlpull.mxp1.MXParserFactory
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserException
import java.io.File
import java.io.IOException
import java.io.InputStream

fun findLastActivityEntryLine(androidAppResourcesPath: String) : Int =
    parse(getManifestFile(androidAppResourcesPath).inputStream())

fun getManifestFile(androidAppResourcesPath: String) =
    File("$androidAppResourcesPath/AndroidManifest.xml")

@Throws(XmlPullParserException::class, IOException::class)
fun parse(inputStream: InputStream): Int {
    inputStream.use { stream ->
        val parser: XmlPullParser = MXParserFactory.newInstance().newPullParser()
        parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false)
        parser.setInput(stream, null)
        parser.nextTag()
        return readManifest(parser)
    }
}


@Throws(XmlPullParserException::class, IOException::class)
private fun readManifest(parser: XmlPullParser): Int {
    var line = 0
    parser.require(XmlPullParser.START_TAG, null, "manifest")

    while (parser.next() != XmlPullParser.END_TAG) {
        if (parser.eventType != XmlPullParser.START_TAG) {
            continue
        }
        // Starts by looking for the entry tag
        if (parser.name == "application") {
            line = readApplication(parser)
        } else {
            skip(parser)
        }
    }
    return line
}

@Throws(IOException::class, XmlPullParserException::class)
private fun readApplication(parser: XmlPullParser): Int {
    var lineNumber = 0

    while (parser.next() != XmlPullParser.END_TAG) {
        if (parser.eventType != XmlPullParser.START_TAG) {
            continue
        }
        when (parser.name) {
            "activity" -> lineNumber = readActivity(parser)
            else -> skip(parser)
        }
    }

    return lineNumber
}

@Throws(IOException::class, XmlPullParserException::class)
private fun readActivity(parser: XmlPullParser): Int {

    parser.require(XmlPullParser.START_TAG, null, "activity")

    while (parser.next() != XmlPullParser.END_TAG) {
        if (parser.eventType != XmlPullParser.START_TAG) {
            continue
        }
        skip(parser)
    }

    return parser.lineNumber
}


@Throws(XmlPullParserException::class, IOException::class)
private fun skip(parser: XmlPullParser) {
    if (parser.eventType != XmlPullParser.START_TAG) {
        throw IllegalStateException()
    }
    var depth = 1
    while (depth != 0) {
        when (parser.next()) {
            XmlPullParser.END_TAG -> depth--
            XmlPullParser.START_TAG -> depth++
        }
    }
}