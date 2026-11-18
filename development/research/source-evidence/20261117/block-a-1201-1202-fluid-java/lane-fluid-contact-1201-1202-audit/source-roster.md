# Frozen source roster — LocalPlayer fluid contact, 1.20.1 → 1.20.2

Assignment: independent Mojmap source review of bounded ordinary water/lava LocalPlayer behavior: (1) body-fluid admission and current push, (2) water/lava vertical response and upward collision exit clearance, (3) fluid-depth cache handoff to held-jump dispatch.

Source root: `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/<release>/mojmap/` (canonical primary local project; read-only). Exact release directories: `1.20.1/mojmap` and `1.20.2/mojmap`. Mapping family: Mojmap. Per `buildSrc/README.md`, use exact-version decompiled source as primary evidence. Both source directories contain only `com/` and `net/`; neither contains `.decompile-provenance.json`. Thus source-file hashes below bind the reviewed local bytes, while upstream pinned artifact/mapping/decompiler provenance is unavailable in this checkout and is not claimed here.

## Frozen paired Java inputs

| Path relative to source root | SHA-256 | Assigned source use |
|---|---|---|
| `net/minecraft/client/player/LocalPlayer.java` (1.20.1) | `69A2D043D2C0595BD364D625A5445E4934D9E07FEF020CD79FAFB42F59952AC2` | LocalPlayer tick/aiStep scheduling and input-held jump state transfer |
| `net/minecraft/client/player/LocalPlayer.java` (1.20.2) | `BB5CBFB03656A1866BB77C00431618BEFE792081223B35DB4BD121ECBB151FD5` | same |
| `net/minecraft/world/entity/Entity.java` (1.20.1) | `94B9C3656715DE2D61FA9A02CCEF164261E50EB13D6090A6F07E58FE9C5B0759` | body AABB fluid scanning/admission, water current push, per-tag depth storage/accessors, upward collision clearance inputs |
| `net/minecraft/world/entity/Entity.java` (1.20.2) | `D7EE49AAEA5E862B92508E767562CABC8F10515605E8FB67565C8C8D5C23B01B` | same |
| `net/minecraft/world/entity/LivingEntity.java` (1.20.1) | `DECF8CD70D194098AD51D0C82E5A0087E0687881E0A2DC66456F2BA6EA76676F` | ordinary water/lava travel vertical response, fluid falling adjustment, aiStep held-jump dispatch |
| `net/minecraft/world/entity/LivingEntity.java` (1.20.2) | `5C481DA1FFC8684C4B30171C92E61FAD751E6A3A708D5BC6486AC9F96FF69828` | same |
| `net/minecraft/world/level/material/FluidState.java` (both releases; same hash) | `0D6D1D24DEB58F056163ACC3AE8976D2C7B3394ACA2AE37FA43BC560C9D33B7C` | tag check, height/amount/flow access semantics consumed by scans and push |
| `net/minecraft/tags/FluidTags.java` (both releases; same hash) | `DF79262149BF0E684D877CA5D2BB87F02D8B66D55ED123D4C2E93BFA6462396F` | water/lava tag keys referenced by the bounded operations |

## Separate dependency and boundary

Effective membership of each fluid state in `minecraft:water` and `minecraft:lava` is a separate data/tag dependency. It is not established by identical Java `FluidTags` declarations and is intentionally not inferred from Java. The decompiled source trees do not include the corresponding runtime tag data, so this dependency is open pending authorized data evidence. This roster does not cover arbitrary custom fluid tags, boats/vehicles, entities other than the LocalPlayer movement path, visual underwater state, damage, or non-movement fluid effects.

This roster is frozen before access to the authorized author claim file. No wiki, backlog, product implementation/history or other lane was consulted.