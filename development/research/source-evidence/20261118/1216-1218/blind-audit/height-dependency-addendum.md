# Append-only required height-source dependency

Applies to frozen roster SHA-256 `D3A172F7F169DA08F8979EA09411F651AD09590D20ABCC83E99DB5E31FCF4FD8`, corrected by `roster-hash-completion.md` SHA-256 `11126612EC8E4A4005D758EA336006FF4F7B466EF13250B739B101FC9FBD1439`. Initial caller review found `FluidState.getHeight` delegates to `Fluid.getHeight`/`getOwnHeight`; exact concrete water/lava fluid subclasses define the consumed height procedure. Add these direct method bodies to the frozen dependency roster before reading them:

- `net/minecraft/world/level/material/Fluid.java` — `e98bdfd14b3092096c6ba763642c3724f852b7589edbf0052b2c8767a874bf94`.
- `net/minecraft/world/level/material/WaterFluid.java` — `9a829ec0d8083d6a3e73a98d40d6c674fd3431aafdb7d05a78cca1daff6e1674`.
- `net/minecraft/world/level/material/LavaFluid.java` — `04134f233b68e7a8856fbf410414eca8ee75f6acb565276d4c4859dc28a6308c`.

These exact source hashes apply to 1.21.6, 1.21.7, and 1.21.8 Mojmap manifests. This is a direct dependency completion inside the original requested surface-height slice, not a new finding lane. It was recorded before reading any of these three source bodies.
