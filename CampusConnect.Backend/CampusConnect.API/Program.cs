using System.Text;
using CampusConnect.API.Data;
using CampusConnect.API.Helpers;
using CampusConnect.API.Interfaces;
using CampusConnect.API.Services;
using Microsoft.AspNetCore.Authentication.JwtBearer;
using Microsoft.EntityFrameworkCore;
using Microsoft.IdentityModel.Tokens;

var builder = WebApplication.CreateBuilder(args);

// ---------------------------------------------------------
// CONTROLLERS + SWAGGER
// ---------------------------------------------------------

builder.Services.AddControllers();
builder.Services.AddEndpointsApiExplorer();
builder.Services.AddSwaggerGen();

// ---------------------------------------------------------
// DATABASE
// ---------------------------------------------------------

builder.Services.AddDbContext<CampusConnectDbContext>(options =>
    options.UseSqlServer(
        builder.Configuration.GetConnectionString("DefaultConnection")
    )
);

// ---------------------------------------------------------
// APPLICATION SERVICES
// ---------------------------------------------------------

builder.Services.AddScoped<JwtHelper>();
builder.Services.AddScoped<IAuthService, AuthService>();
builder.Services.AddScoped<IEventService, EventService>();
builder.Services.AddScoped<IResourceService, ResourceService>();
builder.Services.AddScoped<ILostAndFoundService, LostAndFoundService>();

// ---------------------------------------------------------
// JWT AUTHENTICATION
// ---------------------------------------------------------

var jwtKey =
    builder.Configuration["Jwt:Key"]
    ?? throw new InvalidOperationException("Jwt:Key is missing.");

var jwtIssuer =
    builder.Configuration["Jwt:Issuer"]
    ?? "CampusConnect.API";

var jwtAudience =
    builder.Configuration["Jwt:Audience"]
    ?? "CampusConnect.Android";

builder.Services
    .AddAuthentication(JwtBearerDefaults.AuthenticationScheme)
    .AddJwtBearer(options =>
    {
        options.TokenValidationParameters =
            new TokenValidationParameters
            {
                ValidateIssuerSigningKey = true,

                IssuerSigningKey =
                    new SymmetricSecurityKey(
                        Encoding.UTF8.GetBytes(jwtKey)
                    ),

                ValidateIssuer = true,
                ValidIssuer = jwtIssuer,

                ValidateAudience = true,
                ValidAudience = jwtAudience,

                ValidateLifetime = true,

                ClockSkew = TimeSpan.FromMinutes(1)
            };
    });

builder.Services.AddAuthorization();

// ---------------------------------------------------------
// BUILD APPLICATION
// ---------------------------------------------------------

var app = builder.Build();

// ---------------------------------------------------------
// DEVELOPMENT
// ---------------------------------------------------------

if (app.Environment.IsDevelopment())
{
    app.UseSwagger();
    app.UseSwaggerUI();
}
else
{
    // Production should use HTTPS.
    app.UseHttpsRedirection();
}

// ---------------------------------------------------------
// AUTHENTICATION + CONTROLLERS
// ---------------------------------------------------------

app.UseAuthentication();
app.UseAuthorization();

app.MapControllers();

app.Run();

public partial class Program
{
}