package org.ojai.tests.json;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.InputStream;

import org.junit.Test;
import org.ojai.DocumentReader;
import org.ojai.DocumentReader.EventType;
import org.ojai.DocumentStream;
import org.ojai.json.Json;
import org.ojai.tests.BaseTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//unit test for parsing JSON text in array format
public class TestJsonDocumentStreamFormat extends BaseTest {
  private static Logger logger = LoggerFactory
      .getLogger(TestJsonDocumentStreamFormat.class);

  @Test
  public void testFetchAndParseJsonDocumentStream() throws Exception {
    try (InputStream in = getJsonStream("org/ojai/test/data/manydocs.json");
        DocumentStream stream = Json.newDocumentStream(in)) {

      int documentCount = 0;
      for (DocumentReader reader : stream.documentReaders()) {
        documentCount++;
        if (documentCount == 1) {
          validateDocumentReaderOne(reader);
        } else if (documentCount == 2) {
          validateDocumentReaderTwo(reader);
        } else {
          validateDocumentReaderThree(reader);
        }
      }
      assertEquals(3, documentCount);
    }
  }

  @Test
  public void testEmptyJsonFile() throws Exception {
    try (InputStream in = getJsonStream("org/ojai/test/data/nodocs.json");
        DocumentStream stream = Json.newDocumentStream(in)) {

      int documentCount = 0;
      for(DocumentReader r : stream.documentReaders()) {
        documentCount++;
      }
      assertEquals(0, documentCount);
    }
  }

  @Test
  public void testEmptyJsonFileInArrayFormat() throws Exception {
    try (InputStream in = getJsonStream("org/ojai/test/data/emptyjsonfileinarrayformat.json");
        DocumentStream stream = Json.newDocumentStream(in)) {

      int documentCount = 0;
      for(DocumentReader r : stream.documentReaders()) {
        documentCount++;
      }
      assertEquals(0, documentCount);
    }
  }

  private void validateDocumentReaderOne(DocumentReader r) {
    assertEquals(EventType.START_MAP, r.next());
    assertEquals(EventType.STRING, r.next());
    assertEquals("1", r.getString());
    assertEquals(EventType.START_ARRAY, r.next());
    assertEquals("info", r.getFieldName());
    assertEquals(EventType.START_MAP, r.next());
    assertTrue(!r.inMap());
    assertEquals(EventType.START_MAP, r.next());
    assertEquals("name", r.getFieldName());
    assertEquals(EventType.STRING, r.next());
    assertEquals("John", r.getString());
    assertEquals(EventType.STRING, r.next());
    assertEquals("Doe", r.getString());
    assertEquals("last", r.getFieldName());
    assertEquals(EventType.END_MAP, r.next());
    assertTrue(r.inMap());
    assertEquals(EventType.END_MAP, r.next());
    assertEquals(EventType.START_MAP, r.next());
    assertEquals(EventType.DOUBLE, r.next());
    assertEquals(EventType.END_MAP, r.next());
    assertEquals(EventType.START_MAP, r.next());
    assertEquals(EventType.DOUBLE, r.next());
    assertEquals(EventType.END_MAP, r.next());
    assertEquals(EventType.END_ARRAY, r.next());
    assertEquals(EventType.END_MAP, r.next());
    assertNull(r.next());
  }

  private void validateDocumentReaderTwo(DocumentReader r) {
    assertEquals(EventType.START_MAP, r.next());
    assertEquals(EventType.STRING, r.next());
    assertEquals("2", r.getString());
    assertEquals(EventType.START_MAP, r.next());
    assertEquals("name", r.getFieldName());
    assertEquals(EventType.STRING, r.next());
    assertEquals(EventType.STRING, r.next());
    assertEquals(EventType.END_MAP, r.next());
    assertEquals(EventType.DOUBLE, r.next());
    assertEquals(EventType.END_MAP, r.next());
    assertNull(r.next());
  }

  private void validateDocumentReaderThree(DocumentReader r) {
    assertEquals(EventType.START_MAP, r.next());
    assertEquals(EventType.STRING, r.next());
    assertEquals("3", r.getString());
    assertEquals(EventType.START_ARRAY, r.next());
    assertEquals("name", r.getFieldName());
    assertEquals(EventType.STRING, r.next());
    assertEquals(EventType.STRING, r.next());
    assertEquals(EventType.STRING, r.next());
    assertTrue(!r.inMap());
    assertEquals(EventType.STRING, r.next());
    assertEquals(EventType.START_MAP, r.next());
    assertEquals(EventType.DOUBLE, r.next());
    assertEquals("age", r.getFieldName());
    assertEquals(EventType.END_MAP, r.next());
    assertEquals(EventType.END_ARRAY, r.next());
    assertEquals(EventType.END_MAP, r.next());
    assertNull(r.next());
  }

}
