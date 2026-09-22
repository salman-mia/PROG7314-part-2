namespace CampusConnect.API.DTOs.Resources;

public record ResourceResponse(int Id, string Title, string Description, string ResourceUrl, string ModuleCode, DateTime CreatedAt);
