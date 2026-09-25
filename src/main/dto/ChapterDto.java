package main.dto;

import java.util.List;

public record ChapterDto(
        String id,
        String title,
        int numberScenes,
        int numberLastChoices,
        List<String> choices) {}
