package com.example.pdf;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Map;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PdfService {
	private final SpringTemplateEngine templateEngine;
	
	public byte[] renderTemplateToPdf(String template,
			Map<String, Object> model,
			String baseUrl) {
		Context ctx = new Context();
		model.forEach(ctx::setVariable);
		String html = templateEngine.process(template, ctx);
		
		try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
			PdfRendererBuilder b = new PdfRendererBuilder();
			b.useFastMode();
			b.withHtmlContent(html, baseUrl);
			ClassPathResource font = new ClassPathResource("fonts/NanumGothic.ttf");
			
			try (InputStream is = font.getInputStream()) {
				b.useFont(() -> 
				PdfService.class.getResourceAsStream("/fonts/NanumGothic.ttf"),
						"NanumGothic", 400,
						PdfRendererBuilder.FontStyle.NORMAL, true);
				
				b.defaultTextDirection(PdfRendererBuilder.TextDirection.LTR);
				b.toStream(out);
				b.run();
			}
			return out.toByteArray();
		} catch (Exception e) {
			throw new RuntimeException("PDF 변환 실패", e);
		}
	}
}