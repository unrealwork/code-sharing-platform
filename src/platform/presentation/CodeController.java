package platform.presentation;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import platform.api.CodeService;
import platform.api.model.CodeDto;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/code")
public class CodeController {
    private final CodeService codeService;

    @Value("classpath:views/code-new.html")
    private Resource codeNewView;

    public CodeController(CodeService codeService) {
        this.codeService = codeService;
    }

    @GetMapping(value = "latest")
    public String code(Model model) {
        final List<CodeDto> codes = codeService.latest();
        model.addAttribute("codes", codes);
        model.addAttribute("title","Latest");
        return "code";
    }
    
    @GetMapping("{id}")
    public String idCode(Model model, @PathVariable UUID id) {
        final CodeDto code = codeService.findByIndex(id);
        if (code == null) {
            return null;
        }
        model.addAttribute("codes", Collections.singleton(code));
        model.addAttribute("isDeleted", codeService.isDeleted(id));
        
        return "code";
    }
    
    @GetMapping("new")
    public ResponseEntity<Resource> codenew() {
        return ResponseEntity
                .ok()
                .contentType(MediaType.TEXT_HTML)
                .body(codeNewView);
    }
}
