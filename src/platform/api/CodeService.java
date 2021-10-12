package platform.api;

import platform.api.model.CodeDto;
import platform.api.model.CodeUpdateResult;

import java.util.List;
import java.util.UUID;

public interface CodeService {
    List<CodeDto> latest();

    CodeDto findByIndex(UUID id);

    CodeUpdateResult update(CodeDto code);
    
    boolean isDeleted(UUID id);
}
