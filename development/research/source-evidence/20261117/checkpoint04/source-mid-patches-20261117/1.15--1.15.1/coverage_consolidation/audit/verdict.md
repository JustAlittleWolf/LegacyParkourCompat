# Independent full-pair source-coverage audit — 1.15 → 1.15.1

Verdict: **ACCEPT — complete exact-pair source coverage.** No movement difference was identified in the examined 1.15→1.15.1 source pair. This accepts the source-coverage candidate and permits recording source coverage as frozen for this exact edge. It does not establish product applicability, implementation behavior, server parity, datapack override behavior, intermediate patch onset, or runtime parity.

## Immutable inputs verified

- Coverage candidate: `coverage-candidate.md`, SHA-256 `C2DC73FCD48C2833877B85A1B0A1AAABA34981E2DB7C269DC93318C79AA599B8`.
- Primary source report r4: `ordinary_player_revision_r4/coverage-r4.md`, SHA-256 `2C1E7DE1FDE2838A4B64037E4D51207686F2D41D75D44D1DEFEE514818B9907B`; paired source ledger SHA-256 `2D42123F13239AC2C949F7C3D5B2DD50942F708D872A285B984AB3C972CD07DF`.
- Independent primary r4 audit: `ordinary_player_r4_audit/verdict.md`, SHA-256 `72F721FCFA1E079E0055D4A007C300C32DB66C1E0ABA35C804FFA8112287462F`. It accepts the revision-label correction and reuses the unchanged technical checks from the independent r3 audit, which verified the added reachable `Player.jumpFromGround()` and `Player.travel(Vec3)` overrides, source identities, census, and operation ledger.
- Resource input reference: `resource_gate/input-reference.json`, SHA-256 `7dfc948b8b96eac2598855b320e1f97cd0cc796c83a955aaa13c5c3820baa692`; independent resource audit: `resource_gate/audit/verdict.md`, SHA-256 `A596C9E67F13EDC4E4419142B928B1664C1C8A7B585B175E5FBE3C0B53380B1D`.

All named paths exist and their current bytes match the cited hashes. The exact endpoint identities are Mojmap 1.15 and Mojmap 1.15.1. The accepted source audit verified 3,329 manifest entries per endpoint, no added/removed paths, 12 changed Java files with dispositions, and all 48 operation/provider/callback/effect/tag/response ledger rows against both manifests and disk. The r3 audit's only requested correction after its technical checks was stale revision wording; the accepted r4 audit bound that wording-only correction. No further source supplement or conflicting pair-local source packet was present in the assigned pair scratch.

## Coverage and resource closure

The primary source report covers local-player tick/input and jump/travel overrides, direct position/velocity writers and packet response, entity movement/collision kernels, callbacks, movement-relevant block providers, and attributes/effects/equipment. Its twelve changed Java paths are all dispositioned; the relevant changed `ItemStack`, `Block`, and `AbstractStateHolder` consequences were checked against their exact ordinary-player consumers. The report's residual resource list is exactly closed by the accepted resource packet: water and lava fluid tags, plus fences, walls, leaves, portals, and shulker-box block tags. The independent resource audit verified both official metadata/client artifact chains, the pinned client archives, and all fourteen allowlisted entries (seven per endpoint), including bytes, payloads, paths, and equal endpoint membership. The report's remaining water pathfinding reference is explicitly outside the ordinary direct-player movement graph and does not constitute an open player movement dependency.

The candidate's complete source-coverage conclusion is supported by these bound reports and verdicts. This acceptance is limited to source coverage for the exact 1.15→1.15.1 edge. Datapack overrides, dynamic world state, product applicability, and runtime parity remain separate.
