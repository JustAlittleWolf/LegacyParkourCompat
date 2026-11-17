# Independent source audit: stained glass pane wall predicate

Verdict: **ACCEPT** for author report SHA-256 `fa838210fd9d0aa891f01b1645a8e3220fc1275ffc8655a3635aa5f00ad990ce`.

## Identity and registrations

I recomputed both endpoint `ornithe-feather.sources.sha256` digests. The 1.13.2 digest is `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211`; the 1.14.4 digest is `717b468536348557f9dae8cf57edccf80ad16eb65e5bef6c60bdb5ea3c9f75bc`. Both agree with their ready markers. Every Java hash in the audited report matches the current endpoint source manifest and file bytes. I additionally checked the 1.13.2 `Blocks.java` registration file (SHA-256 `3e128c9a4f6b53e1037fb85269c4d00bc85112ae52fe48f9534232e13ee2a303`). Its registrations contain all 16 named stained-glass-pane keys; the accepted `glass_pane_crosswalk` independently checks the same 16 class assignments against both endpoint source manifests.

Both `StainedGlassPaneBlock` classes declare four directional Boolean properties and `WATERLOGGED`; the default state initializes all five. The Cartesian registered state domain is 32 states per key. The old class inherits `IronBarsBlock.getFaceShape` through `GlassPaneBlock`; its horizontal face result is unconditionally `MIDDLE_POLE_THIN`. The old wall predicate does not admit that value: it admits non-exception `SOLID`, `MIDDLE_POLE_THICK`, or `MIDDLE_POLE` for a fence gate. Thus all 16 keys and all 32 states produce false for each horizontal direction.

## Target predicate and resources

The 1.14.4 `WallBlock` predicate accepts a full collision face unless an attachment exception applies, or accepts a `WALLS` tag member or eligible fence gate. `BlockState.m_87223014` resolves through `Block.isFaceSolid` to a full collision-face check. `StainedGlassPaneBlock` is not a fence gate and is not an attachment exception. The target `PaneBlock` shape builder uses a two-unit-wide center post and optional two-unit-wide arms on the 0–16 grid; any arm reaching a queried horizontal boundary occupies only a 2/16 tangent-width strip. Therefore no registered pane state has a full horizontal collision face. The wall tag payload at `wall_resource_membership/payloads/data/minecraft/tags/blocks/walls.json` has SHA-256 `f20174f51f66d27681fd9d469a77c86cae82012c0d9125e7d246c426b772391c`; I parsed its 14 values and confirmed none names a stained glass pane. Its extraction record is bound to the pinned 1.14.4 client artifact and has independent resource verdict SHA-256 `6402c6af692b3f198c853d3558b91d4de7c215db793e8847e66e45f08f4d8898`.

Consequently the target wall bit is also false in every one of the 16 × 32 × 4 checked key/state/direction cases. The report's no-difference result is supported.

## Limits

Acceptance is limited to the stained-pane wall-neighbor predicate at these exact endpoints. Pane directional-state production, other neighbor classes, movement outcomes, intermediate patch introduction and full-pair coverage/audit remain open. No correction addendum is needed.
