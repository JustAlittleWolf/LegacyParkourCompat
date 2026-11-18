# Append-only evidence roster completion

Applies to the immutable frozen roster SHA-256 `D3A172F7F169DA08F8979EA09411F651AD09590D20ABCC83E99DB5E31FCF4FD8` and correction SHA-256 `5192181DAB36E523109DF73E630ABAB6BBB5F6E724904356BECDF0E9685E9EC2`. Before reading compared source bodies, complete the exact hash roster for files already listed there:

- `net/minecraft/world/level/material/Fluids.java` — `913cb5d937af92a7f90f7dc233a3d02c05f75e70a2a740300b2f26eedcbed931`.
- `net/minecraft/world/level/dimension/BuiltinDimensionTypes.java` — `4971dde1dd0b3b15290b84ba3a4396b0e770d67e30f48e84a59fe82c64dac89c`.
- `net/minecraft/world/level/dimension/DimensionDefaults.java` — `1d8bf277044f8a9470c0fae96a9ab7e408de1df1a8ca907444f7ad4df545119d`.

These exact path/hash pairs apply identically to the 1.21.6, 1.21.7, and 1.21.8 Mojmap source manifests. The two additional dimension sources were listed in the frozen roster as conditional dependencies; `DimensionDefaults` is recorded here as a nearby allowable dependency if a frozen caller demonstrates it is required. No source bodies had been read when this completion was recorded.
