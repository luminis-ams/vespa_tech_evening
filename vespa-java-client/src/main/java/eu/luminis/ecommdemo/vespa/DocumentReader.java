package eu.luminis.ecommdemo.vespa;

import com.yahoo.document.Document;
import com.yahoo.document.DocumentId;
import com.yahoo.documentapi.DocumentAccess;
import com.yahoo.documentapi.SyncParameters;
import com.yahoo.documentapi.SyncSession;

import static eu.luminis.ecommdemo.vespa.VespaDataFeeder.ID_FORMAT;

public class DocumentReader {
    public static void main(String[] args) {
        DocumentAccess access = DocumentAccess.createForNonContainer();
        DocumentId id = new DocumentId(String.format(ID_FORMAT, 1));
        SyncSession session = access.createSyncSession(new SyncParameters.Builder().build());

        Document docOut = session.get(id);
        System.out.println("document get:" + docOut.toJson());

        session.destroy();
        access.shutdown();
    }
}
