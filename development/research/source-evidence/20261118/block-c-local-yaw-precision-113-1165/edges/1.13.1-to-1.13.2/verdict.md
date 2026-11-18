# Independent audit: local yaw precision, Feather 1.13.1 → 1.13.2

**Verdict: ACCEPT the scoped no-difference claim.** The direct local camera yaw update and its first ordinary dry ground/air yaw-relative acceleration use are unchanged in these exact releases. This accepts only the bounded assertion in the source draft; it does not close the full release-pair inventory or prove behavior on a running client.

## Audited claim and source identity

Audited source draft: `c-yaw-precision-1131-1132-source/report.md`, SHA-256 `9170a01a3d758d29201e94e33185fa293412c445128ca2257d699f72a34a4fd2`.

Both local ready markers identify `ornithe-feather` Feather Gen2 build.2 and the exact releases. The 1.13.1 ready marker and provenance bind source-manifest SHA-256 `21770b01ff3bf443db2e9e1b6df7db25d2fd6b7a72a86228259269c932d3abba`, client SHA-256 `f0b4cf6d1a87ae150f87a535651138ca7b82bdad8853f76e7ddbff1dd610ac87`, and mapping coordinate `net.ornithemc:feather-gen2:1.13.1+build.2`; the actual ready, provenance, and source-manifest file hashes match their references. The 1.13.2 ready marker binds source-manifest SHA-256 `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211`, client SHA-256 `3410887ba652f25792c7675bfaf9140e73b60e93cfbf113a803f8a98cb05c0f9`, and its Feather Gen2 build.2 mapping names; the source-manifest file hash matches. Its compact provenance sidecar agrees on version 1.13.2 and namespace `ornithe-feather`. The 1.13.2 verification sidecar reports 2,711 published sources checked with zero source-file differences, zero missing sources, and zero raw-input-artifact differences. Its noted difference is a regenerated intermediate remapped jar, not a published source difference.

For both releases, source-manifest row hashes were independently checked against actual bytes for all cited files; the hashes also match between releases:

- `net/minecraft/client/MouseHandler.java` — `b045dc9eaf5b5ab1d9f13dd795465a106f52571dda9e5c837f5c2bbbdfddf4ce`
- `net/minecraft/entity/Entity.java` — `1d6ec8b80f74635401745c2c027bf36555c85348ca5693764f2668363b17d269`
- `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java` — `2583495f3a02b4791aa036e6a8d354d7596c4984761969a0f29b29d8d9bf42bf`
- `net/minecraft/entity/living/LivingEntity.java` — `bb691358c9a43c9f46e85575bf4d0a4ad671d0eb912502acc3a6a3f625e42f1c`
- `net/minecraft/util/math/MathHelper.java` — `2211b0cb3da7d8789a4a4df96509b95e6b1be8b222be2f530e7bd0695573bd69`

The ready-manifest sizes also agree with the actual Java trees: 2,710 files / 14,768,136 bytes for 1.13.1 and 2,711 files / 14,770,525 bytes for 1.13.2.

## Independent trace and findings

`MouseHandler` passes its cursor deltas to `Entity.updateLocalPlayerCamera`. In that method (`Entity.java:318–329`), `cursorDeltaX * 0.15` is held in a `double`; current yaw is assigned as `(float)(this.yaw + e)`, then previous yaw as `(float)(this.lastYaw + e)`. The current-yaw write precedes the previous-yaw write. Both additions therefore widen the stored float yaw to double for the addition and narrow back to float separately. Neither yaw write is normalized, clamped, or guarded for finite input. Pitch follows separate clamp logic and is outside this yaw-only claim. These operation widths, assignments, and order match exactly in both releases.

The first ordinary dry ground/air use is reachable for the local camera player: its `serverTickAi` copies input into float movement fields; the living tick’s travel section calls `moveRelative`; under the local/update-side gate and the dry, non-fall-flying branch, ground scale is `getSpeed() * (0.16277137F / (slipperiness * 0.91F)^3)` and air scale is `speedInAir`. Both then call `Entity.updateVelocity(float, float, float, float)`. That helper’s squared-input threshold, float normalization, scale division, float sine/cosine of current yaw, and X/Y/Z addition order are unchanged (`Entity.java:1057–1074`). `MathHelper.sin/cos` use the same float table-index conversion in both releases. The resulting velocity enters the existing `move(MoverType.SELF, ...)` collision path. For completeness, a NaN yaw is not rejected by the turn method; Java’s float-to-int conversion in the sine-table index maps NaN to index zero. That behavior is also identical here.

## Scope limit

This is a source-only audit of the cited direct camera-yaw update and its first ordinary dry ground/air acceleration dependency. Swimming, fluids, fall-flying, other yaw writers, later movement consumers, and the rest of the pair inventory were not audited by this verdict. No movement or runtime test was run.
## Phase two revalidation

The authorized source draft was rehashed before and after this append and remains SHA-256 `9170a01a3d758d29201e94e33185fa293412c445128ca2257d699f72a34a4fd2`. Its bytes were not modified. The original verdict content above is preserved byte-for-byte as the prefix of this file; this append records only the revalidation and does not expand the audited claim.