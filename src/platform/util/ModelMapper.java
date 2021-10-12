package platform.util;

import platform.api.model.CodeDto;
import platform.persistence.Code;

import java.time.Instant;

public class ModelMapper {
    private ModelMapper() {

    }

    public static CodeDto toDto(final Code code) {
        final String date = Instant.ofEpochMilli(code.getTs()).toString();

        return CodeDto.of(code.getCode(), date,
                code.getTime(), code.getViews());
    }

    public static Code toEntity(final CodeDto codeDto) {
        final Code code = new Code();
        if (codeDto.getDate() != null) {
            code.setTs(Instant.parse(codeDto.getDate()).toEpochMilli());
        }
        if (codeDto.getTime() != null) {
            code.setTime(codeDto.getTime());
        }

        if (codeDto.getViews() != null) {
            code.setViews(codeDto.getViews());
        }
        code.setCode(codeDto.getCode());
        return code;
    }
}
