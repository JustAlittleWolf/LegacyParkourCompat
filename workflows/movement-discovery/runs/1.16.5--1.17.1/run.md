# Discovery: 1.16.5 to 1.17.1

- Status: active
- Scope: client player movement; A = `1.16.5`; B = `1.17.1`.
- Repository revision/start date: `c133c29`; 2026-09-26.
- Namespace: official Mojang names; explicit `mojmap` CLI mode on both sides. Each release uses its own official client mappings artifact and is independently remapped to `named`.
- B command: `gradlew.bat --no-daemon --rerun-tasks decompileMinecraft --versions=1.17.1 --mappings=mojmap`; successful log at ignored `build/movement-discovery/1.17.1-mojmap.log`.
- A: source owner and coordinator confirmed exact 1.16.5 Mojmap task success. A's raw log is not in the accessible worktree; artifact hashes are recorded below, and the raw log remains a provenance closure item.
- Toolchain: Gradle 9.7.1; JDK 25.0.3+9-LTS for Vineflower (B log); target bytecode Java 16 (B); Vineflower 1.12.0; Tiny Remapper 0.14.1; Mapping IO 0.9.1; Gson 2.14.0; ASM 9.10.1; default 4G heap and repository options.

## Artifact manifest

Hashes are SHA-256 unless identified as publisher SHA-1. Source roots are under the shared ignored `decompiled_minecraft` directory. Cache paths below are local and ignored.

### A — 1.16.5

- Requested/resolved `1.16.5`; exact resolution and successful task completion reported by source owner.
- Source root: `decompiled_minecraft/1.16.5/mojmap/`.
- Owner cache client jar `build/minecraft-decompile-cache/1.16.5/client.jar`: SHA-256 `00B5EBBC33E95EA88C1AB80601599C9827E9AA861D93CCC2C7E3AAA281AB00C8`; Mojang SHA-1 `37fd3c903861eeff3bc24b71eed48f828b5269c8`.
- `version.json` SHA-256 `3EEEAB7B3165CC5263DC26FF8FE114FDB718B75A0244EFEAD9D19635D090BA72`.
- `mojmap`; official mapping artifact URL `https://piston-data.mojang.com/v1/objects/374c6b789574afbdc901371207155661e0509e17/client.txt`; cached `client_mappings.txt` SHA-256 `7931ED6D723ECEB1D621D05A76E10DDF643BF468C6ECF1C4ECF377BD72CF8B8C`; Mojang SHA-1 `374c6b789574afbdc901371207155661e0509e17`.
- Remapped `client-mojmap.jar` SHA-256 `18DA764651703F18941A800D4421C4FC28DB39C1586060C2C7E3AAA281AB00C8`.
- Raw successful decompiler log unavailable locally; do not claim a persisted log.

### B — 1.17.1

- Requested/resolved `1.17.1`; exact ID confirmed in successful log.
- Source root: `decompiled_minecraft/1.17.1/mojmap/` (worktree junction to shared ignored root).
- `client.jar` SHA-256 `A49B4A56C5BBE15C9ED9FE53EFA9A591F265A1F5BA7D6AA9739A58EA7A92B79D`; Mojang SHA-1 `8d9b65467c7913fcf6f5b2e729d44a1e00fde150`.
- `version.json` SHA-256 `B6125F5A4410C3A71C1CF177DBDBF8C9E409BDE8E435FB094BEDD5AD6CE07902`.
- `mojmap`; official mapping artifact URL `https://piston-data.mojang.com/v1/objects/e4d540e0cba05a6097e885dffdf363e621f87d3f/client.txt`; `client_mappings.txt` SHA-256 `2B28DED68F8602AAF2F35EF92DD10EE41BA2CF9723E29570155D60E1723848E9`; Mojang SHA-1 `e4d540e0cba05a6097e885dffdf363e621f87d3f`.
- Remapped `client-mojmap.jar` SHA-256 `E85FCB0A8656EACE49020D2B5824D33FAD25FB53E7CCABA0C3DE2DBC6CC0E923`.
- Log `build/movement-discovery/1.17.1-mojmap.log`; successful completion, plus two Vineflower duplicate-lambda processing notices in `ModelBakery`.

### Cited source hashes

Paths are relative to each version's Mojmap source root; hashes are SHA-256.

| Role | Path | A | B |
| --- | --- | --- | --- |
| Local player | `net/minecraft/client/player/LocalPlayer.java` | `6011569E766BB1568609147BE9AA14E9C08C51948E3D3A60FD066E848F6A8C2B` | `C9A91CB6CB57806BC8D22E5BFE2D97DAAF21D5D2A48F34A6E2C53164C61C5812` |
| Keyboard producer | `net/minecraft/client/player/KeyboardInput.java` | `746EA654CF4F46A5F4B94A237C4307652252A44807A488B606DC993E088396F7` | `EA41065C909E53F1A2CC29ECDB6A9A8F9265D2801CD8B95B996E182C318ECD69` |
| Input state | `net/minecraft/client/player/Input.java` | `367C3A9B0B21D8F106A21FD2C73A3018685DBF07D9C8A9340E2D4C9D73359201` | same |

Resources are not yet inventoried. Java source output omits jar resources; relevant tags, defaults, data and server-supplied inputs remain queued.

## Correspondence and call order

See [navigation.md](navigation.md). Names and signatures are resolved independently in both exact Mojmap trees; no name-only inference is used for a behavioral conclusion.

## Coverage ledger

- `S1-INPUT-KEYBOARD`, stage 1: `compared-no-difference`, scoped to `KeyboardInput.tick(boolean)` and `Input` state access. Both sides sample directional/jump/shift controls, derive impulses with identical equality/ternary expressions, then when the same boolean argument is true scale each impulse by `(float)(impulse * 0.3)`. B declares `MOVING_SLOW_FACTOR = 0.3`, but the method uses the same `0.3` double literal. The expression order and cast match; `Input.java` is byte-identical by SHA-256. The meaning of the boolean argument and all other local-player input branches are separate slices.
- Stage 1 remaining slices: `pending`.
- Stages 2–7: `pending`; see navigation index and queue.

## Dependency queue and blockers

- `A-LOG`: obtain the 1.16.5 owner's raw successful log or preserve its transcript location; completion was confirmed, but no raw log is present in the accessible owner build directory.
- `RESOURCES`: inspect exact client-jar resources and relevant server-supplied inputs for stages 5–7.
- `DECOMPILER-WARNINGS`: determine whether B's two `ModelBakery` duplicate-lambda warnings intersect any movement dependency; otherwise disposition them as unrelated.

## Finding index

None. The input slice is a bounded no-difference result, not a trajectory or runtime claim.

## Resume checkpoint

- Last completed slice: `S1-INPUT-KEYBOARD`.
- Next: compare local-player `aiStep()` input/tick ordering using A lines 627–677 and B lines 649–699, then split sprint, jump/auto-jump, item-use, flight and riding paths.
- Outstanding: A raw log, source hashes and dependencies for future slices, resource/data review.
- Runtime validation: not performed.

## Source audit closure

Partial and in progress. One bounded slice is compared; all other navigation work is pending. This catalog does not claim exhaustive equivalence.
