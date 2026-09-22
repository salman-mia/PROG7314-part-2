using System.ComponentModel.DataAnnotations;

namespace CampusConnect.API.DTOs.Resources;

public class CreateResourceRequest
{
    [Required, StringLength(150)] public string Title { get; set; } = string.Empty;
    [Required, StringLength(1000)] public string Description { get; set; } = string.Empty;
    [Required, Url] public string ResourceUrl { get; set; } = string.Empty;
    [Required, StringLength(30)] public string ModuleCode { get; set; } = string.Empty;
}
