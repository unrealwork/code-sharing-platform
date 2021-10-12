package platform.api;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import platform.api.model.CodeDto;
import platform.api.model.CodeUpdateResult;
import platform.persistence.Code;
import platform.persistence.CodeRepository;
import platform.util.ModelMapper;

import javax.transaction.Transactional;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CodeServiceImpl implements CodeService {
    private final CodeRepository codeRepository;

    @Override
    public List<CodeDto> latest() {
        Pageable limit = PageRequest.of(0, 10);
        return codeRepository.findByTimeLessThanEqualAndViewsLessThanEqualOrderByTsDesc(0, 0, limit)
                .stream().map(ModelMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public CodeDto findByIndex(UUID id) {
        return codeRepository.findById(id).map(c -> {
            final CodeDto codeDto = ModelMapper.toDto(c);
            if (c.getViews() > 0) {
                int views = c.getViews() - 1;
                c.setViews(views);
                if (views == 0) {
                    codeRepository.delete(c);
                } else {
                    codeRepository.save(c);
                }
            }
            if (c.getTime() > 0) {
                long duration = ChronoUnit.SECONDS.between(Instant.ofEpochMilli(c.getTs()), Instant.now());
                if (duration > c.getTime()) {
                    codeRepository.delete(c);
                    return null;
                }
                codeDto.setTime(c.getTime() - (int) duration);
            }
            return codeDto;
        }).orElse(null);
    }

    @Override
    public CodeUpdateResult update(CodeDto newCode) {
        final Code code = ModelMapper.toEntity(newCode);
        code.setTs(System.currentTimeMillis());
        final Code codeUpdated = codeRepository.save(code);
        return new CodeUpdateResult(codeUpdated.getId().toString());
    }

    @Override
    public boolean isDeleted(UUID id) {
        return codeRepository.findById(id).isEmpty();
    }
}
