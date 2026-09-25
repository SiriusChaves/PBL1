package main.dto;

import java.util.List;

public record ChapterDtoRecord(
        String id,
        String title,
        int numberScenes,
        int numberLastChoices,
        List<String> choices) {}
