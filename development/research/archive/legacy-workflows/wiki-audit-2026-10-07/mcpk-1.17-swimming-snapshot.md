# MCPK finding snapshot: 1.17 swimming entry gate

Snapshot date: 2026-10-08. Lane: MCPK wiki audit. Status: **source-confirmed bounded finding; independent wiki-lane review pending**. This snapshot does not freeze the full release pair.

## MCPK claim

The MCPK [Version Differences page](https://www.mcpk.wiki/wiki/Version_Differences), last edited 2026-04-17 12:51, lists a 1.17 change requiring water at the player’s block position to start swimming. This snapshot checks the entry predicate against the preceding 1.16.5 endpoint and distinguishes entering from remaining in the swimming state.

## Source adjudication

The 1.16.5 Mojmap `Entity.updateSwimming` starts swimming when the entity is sprinting, underwater, and not riding a vehicle (`Entity.java:949–954`). The 1.17.1 version adds a further requirement that the fluid at `blockPosition` is in `FluidTags.WATER` when the entity is not already swimming (`Entity.java:1056–1064`). The changed guard is on entry only:

- When already swimming, both versions keep swimming while sprinting, in water, and not a passenger (`1.16.5:950–951`; `1.17.1:1057–1058`). This continuation path does not use the new block-position fluid test.
- When not swimming, 1.16.5 uses sprinting + underwater + not passenger. In 1.17.1 the same conditions are ANDed with `level.getFluidState(blockPosition).is(FluidTags.WATER)` (`:1060–1062`). The predicate is a fluid-tag test at the entity’s block position, not a check that every part of its bounding box lies in source water.

This confirms the endpoint predicate change behind the wiki entry. It does not establish the exact first 1.17 patch, pose dimensions, fluid-height boundary, or subsequent swimming movement math.

## Exact source identities

Canonical ready root: `D:\Javastuff\LegacyParkourCompat\build\movement-campaign-2026-10-07\ready\`.

Both endpoints are ready Mojmap trees. The ready source and artifact manifest hashes identify the endpoints; the cited `Entity.java` hashes match their source-manifest rows.

| Endpoint | Source manifest SHA-256 | Artifact manifest SHA-256 |
|---|---|---|
| `1.16.5/mojmap` | `9499f2611d0e6dde37cb635f1a28a591416382d6bc188b37d5f99e2b2a20823b` | `f9b9812d6995012cefcc1201a63855e5931e8551b508753be6c46d442d8b370c` |
| `1.17.1/mojmap` | `93270d229acfb751bf56daf1e7be26ce3dcff26b29e94aa157de621405a3463b` | `e52c5dbae7d9663190ccc55a4f9b44a8e0615fb1fbbd8280811df43f70ec8aaa` |

Cited Java file SHA-256 values, relative to each endpoint’s `mojmap/` directory:

- `1.16.5`: `net/minecraft/world/entity/Entity.java` — `f9a9a073fe3105a0aa53d0f21ec72e59084e8d21a14c1cd3be75703865ee2666`.
- `1.17.1`: `net/minecraft/world/entity/Entity.java` — `ab28e1fba924771ec048140dfd293ee5a46a7dfe81f71a1a0b1aecc1927232de`.

This is a bounded source finding independent of other audit lanes. Broad release-pair coverage and independent wiki-lane acceptance remain open.