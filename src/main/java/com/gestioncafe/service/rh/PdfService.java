package com.gestioncafe.service.rh;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;

@Service
public class PdfService {

    @Autowired
    private ResourceLoader resourceLoader;

    public void downloadCandidatPdf(Long candidatId, HttpServletResponse response) throws IOException {
    // Essayons plusieurs noms possibles
    String[] candidateNames = {
        "candidatCv" + candidatId + ".pdf",                   // candidatCv1.pdf
        String.format("candidatCv%02d.pdf", candidatId),      // candidatCv01.pdf
        String.format("candidatCv%03d.pdf", candidatId),      // candidatCv001.pdf
        String.format("candidatCv%04d.pdf", candidatId)       // candidatCv0001.pdf
    };

    Resource resource = null;

    for (String filename : candidateNames) {
        Resource attempt = resourceLoader.getResource("classpath:static/uploads/cv/" + filename);
        if (attempt.exists() && attempt.isReadable()) {
            resource = attempt;
            break;
        }
    }

    if (resource == null || !resource.exists()) {
        response.setStatus(HttpServletResponse.SC_NO_CONTENT); // 204 = rien à faire
        return;
    }

    String filename = resource.getFilename(); // le bon nom
    response.setContentType("application/pdf");
    response.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");
    response.setContentLengthLong(resource.contentLength());

    try {
        Files.copy(resource.getFile().toPath(), response.getOutputStream());
        response.getOutputStream().flush();
    } catch (IOException e) {
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        response.setContentType("text/plain; charset=UTF-8");
        response.getWriter().write("Erreur lors de l'envoi du fichier: " + e.getMessage());
    }
}


}
