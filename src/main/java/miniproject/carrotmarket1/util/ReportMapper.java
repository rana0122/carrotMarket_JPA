package miniproject.carrotmarket1.util;

import miniproject.carrotmarket1.dto.ReportDTO;
import miniproject.carrotmarket1.entity.Report;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ReportMapper {

    private final ModelMapper modelMapper;

    public ReportMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;

        // 커스텀 매핑 설정
        modelMapper.typeMap(Report.class, ReportDTO.class).addMappings(mapper -> {
            mapper.map(Report::getProduct, ReportDTO::setProduct);
            mapper.map(Report::getReporter, ReportDTO::setReporter);
            mapper.map(Report::getCategory, ReportDTO::setCategory);
            mapper.map(src -> {
                if (src.getAdmin() != null) {
                    return src.getAdmin().getId();
                }
                return null;
            }, ReportDTO::setAdmin);
        });
    }

    // Entity -> DTO 변환
    public ReportDTO toDto(Report report) {
        return modelMapper.map(report, ReportDTO.class);
    }

    // DTO -> Entity 변환
    public Report toEntity(ReportDTO reportDTO) {
        return modelMapper.map(reportDTO, Report.class);
    }

    // List<Entity> -> List<DTO> 변환
    public List<ReportDTO> toDtoList(List<Report> reports) {
        return reports.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    // List<DTO> -> List<Entity> 변환
    public List<Report> toEntityList(List<ReportDTO> reportDTOs) {
        return reportDTOs.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }
}

