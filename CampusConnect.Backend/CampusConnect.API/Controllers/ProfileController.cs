using System.Security.Claims;
using CampusConnect.API.Data;
using CampusConnect.API.DTOs.Profile;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;

namespace CampusConnect.API.Controllers;

[ApiController]
[Authorize]
[Route("api/profile")]
public class ProfileController : ControllerBase
{
    private readonly CampusConnectDbContext _db;
    public ProfileController(CampusConnectDbContext db) => _db = db;
    private int UserId => int.Parse(User.FindFirstValue(ClaimTypes.NameIdentifier)!);

    [HttpGet]
    public async Task<IActionResult> Get()
    {
        var user = await _db.Users.AsNoTracking().SingleOrDefaultAsync(u => u.Id == UserId);
        return user is null ? NotFound() : Ok(new ProfileResponse(user.Id, user.FullName, user.Email, user.PreferredTheme, user.NotificationsEnabled));
    }

    [HttpPut]
    public async Task<IActionResult> Update(UpdateProfileRequest request)
    {
        var user = await _db.Users.SingleOrDefaultAsync(u => u.Id == UserId);
        if (user is null) return NotFound();
        user.FullName = request.FullName.Trim();
        user.PreferredTheme = request.PreferredTheme;
        user.NotificationsEnabled = request.NotificationsEnabled;
        await _db.SaveChangesAsync();
        return Ok(new ProfileResponse(user.Id, user.FullName, user.Email, user.PreferredTheme, user.NotificationsEnabled));
    }
}
