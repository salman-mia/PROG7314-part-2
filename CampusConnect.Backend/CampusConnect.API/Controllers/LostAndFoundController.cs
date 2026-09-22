using System.Security.Claims;
using CampusConnect.API.DTOs.LostAndFound;
using CampusConnect.API.Interfaces;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;

namespace CampusConnect.API.Controllers;

[ApiController]
[Route("api/[controller]")]
public class LostAndFoundController : ControllerBase
{
    private readonly ILostAndFoundService _service;
    public LostAndFoundController(ILostAndFoundService service) => _service = service;
    private int UserId => int.Parse(User.FindFirstValue(ClaimTypes.NameIdentifier)!);

    [AllowAnonymous, HttpGet]
    public async Task<IActionResult> GetAll() => Ok(await _service.GetAllAsync());

    [AllowAnonymous, HttpGet("{id:int}")]
    public async Task<IActionResult> GetById(int id) => (await _service.GetByIdAsync(id)) is { } item ? Ok(item) : NotFound();

    [Authorize, HttpPost]
    public async Task<IActionResult> Create(CreateLostAndFoundRequest request)
    {
        var created = await _service.CreateAsync(request, UserId);
        return CreatedAtAction(nameof(GetById), new { id = created.Id }, created);
    }

    [Authorize, HttpPut("{id:int}")]
    public async Task<IActionResult> Update(int id, UpdateLostAndFoundRequest request) => (await _service.UpdateAsync(id, request, UserId)) is { } item ? Ok(item) : NotFound(new { message = "Item not found or you are not the owner." });

    [Authorize, HttpDelete("{id:int}")]
    public async Task<IActionResult> Delete(int id) => await _service.DeleteAsync(id, UserId) ? NoContent() : NotFound(new { message = "Item not found or you are not the owner." });
}
