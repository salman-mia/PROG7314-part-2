using CampusConnect.API.DTOs.Resources;
using CampusConnect.API.Interfaces;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;

namespace CampusConnect.API.Controllers;

[ApiController]
[Route("api/[controller]")]
public class ResourcesController : ControllerBase
{
    private readonly IResourceService _service;
    public ResourcesController(IResourceService service) => _service = service;

    [AllowAnonymous, HttpGet]
    public async Task<IActionResult> GetAll() => Ok(await _service.GetAllAsync());

    [AllowAnonymous, HttpGet("{id:int}")]
    public async Task<IActionResult> GetById(int id) => (await _service.GetByIdAsync(id)) is { } item ? Ok(item) : NotFound();

    [Authorize, HttpPost]
    public async Task<IActionResult> Create(CreateResourceRequest request)
    {
        var created = await _service.CreateAsync(request);
        return CreatedAtAction(nameof(GetById), new { id = created.Id }, created);
    }

    [Authorize, HttpPut("{id:int}")]
    public async Task<IActionResult> Update(int id, UpdateResourceRequest request) => (await _service.UpdateAsync(id, request)) is { } item ? Ok(item) : NotFound();

    [Authorize, HttpDelete("{id:int}")]
    public async Task<IActionResult> Delete(int id) => await _service.DeleteAsync(id) ? NoContent() : NotFound();
}
