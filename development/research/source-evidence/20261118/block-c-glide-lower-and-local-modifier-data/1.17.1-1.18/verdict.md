# Independent audit: Elytra lower gate at climbable feet

**Verdict: accepted, unchanged for the bounded operation.** The frozen report payload was verified at SHA-256 `7d0db2cef6a8973477996de2999785421a5329e5dcce236e412225196845e85a`.

## Exact source identity

Reviewed canonical sources are `decompiled_minecraft/1.17.1/mojmap/` and `decompiled_minecraft/1.18/mojmap/` (Mojmap). In both pairs of adjacent sidecars, the ready marker says `status: ready`, names the exact version and mapping, and its `sourceManifestSha256` equals the SHA-256 of `mojmap.sources.sha256`. I independently checked every manifest-listed source file against its digest and the complete `.java` file count: 1.17.1, 4,142 entries/files with zero mismatches; 1.18, 4,203 entries/files with zero mismatches.

| Evidence | 1.17.1 | 1.18 |
|---|---|---|
| Source-manifest SHA-256 (also ready-marker value) | `93270d229acfb751bf56daf1e7be26ce3dcff26b29e94aa157de621405a3463b` | `a43c61223ddedbdd8ede4d2daf6a750a58cbba324b30c025429ee1030cfc0d78` |
| `mojmap.provenance.json` SHA-256 | `de197f77473955635e8c5730e113f227af2b8af9306b74e8a54a765eafc5a333` | `e642f075b25d1b6e74bad05edacd740421c70f5d8e0c2a38767523cf864124c5` |
| Exact provenance identity | `versionId=1.17.1`, `namespace=mojmap`; exact run includes 1.17.1 | `versionId=requestedVersionId=1.18`, `namespace=mojmap` |
| Ready-marker provenance binding | No provenance digest field; sidecar independently present and hashed | `provenanceSha256` matches the adjacent provenance sidecar |
| `LivingEntity.java` SHA-256 | `33fd081aadb2b6fdc9ebf487db6da5b38c54f4b8676572790ee2203690d15e6f` | `68fc9b15eb31690377afe92acfc783c86945fedec5739d28faacd96da4f9e0e7` |
| `LocalPlayer.java` SHA-256 | `c9a91cb6cb57806bc8d22e5bfe2d97daaf21d5d2a48f34a6e2c53164c61c5812` | `e38d0bbb6e5b9de2a698609b8491d0007406b3becd42ba9b95289f533788c996` |

The 1.17.1 marker does not cryptographically bind the adjacent provenance sidecar; the report correctly discloses this. Its ready marker does bind the exact source-manifest digest and exact client digest. The 1.18 marker binds both the source manifest and provenance digest. No regeneration was performed.

## Behavioral comparison

- `LivingEntity.travel(Vec3)`: 1.17.1 lines 2003–2111; 1.18 lines 2005–2113. After the dry water/lava exclusions, both choose `else if (this.isFallFlying())`. At 1.17.1 lines 2068–2072 and 1.18 lines 2070–2074, the current movement's `Vec3.y` is compared as a `double` with strict `> -0.5`; only the true arm writes the float literal `1.0F` to `fallDistance`. Equality does not enter it.
- That write precedes look-angle and glide calculations. In each version the calculated glide velocity is then stored and `move(MoverType.SELF, this.getDeltaMovement())` follows immediately (1.17.1 lines 2096–2097; 1.18 lines 2098–2099). There is no `onClimbable()` call or climbable cleanup in this fall-flying branch before that move. A conditional slow-falling reset exists earlier in `travel` (1.17.1 lines 2006–2010; 1.18 lines 2008–2012); it is not climbable cleanup, and the qualifying lower-gate write follows it.
- The ladder/vine feet predicate is equivalent: `onClimbable()` rejects spectators, reads `getFeetBlockState()`, and accepts `BlockTags.CLIMBABLE` before its separate trapdoor case (1.17.1 lines 1427–1443; 1.18 lines 1429–1445). That predicate is not consulted by the active glide branch.
- The ordinary friction path does perform climbable cleanup through `handleOnClimbable` before its move (1.17.1 `travel` lines 2111–2135, helper lines 2155–2158 and 2183–2186; 1.18 lines 2113–2137, helper lines 2157–2160 and 2185–2188). The implementation changed from direct `fallDistance = 0.0F` to `resetFallDistance()`; the 1.18 method at `Entity.java` lines 2101–2103 assigns the same `0.0F`. Because `isFallFlying()` selects the preceding branch, this ordinary climbable cleanup is bypassed for the audited glide tick.

## Boundaries and limitations

The claim is about an already-active fall-flying state entering `LivingEntity.travel` while dry, locally/effectively controlled, non-spectator and at ladder/vine feet. It does not prove how that state became active there. `LocalPlayer.aiStep()`'s matching request condition in 1.17.1 lines 747–751 and 1.18 lines 745–749 requires `!onClimbable()` as well as jump input and other gates, then checks the enabled chest Elytra and calls `tryToStartFallFlying()` before sending the request. Thus ordinary local initiation at those feet is excluded; aliases, server acknowledgement, prior-state routes and other activation mechanisms are not resolved here. The travel branch itself does not check chest-slot equipment.

The `1.0F` write is pre-move, not an end-of-tick invariant. `Entity.move` synchronously calls `checkFallDamage` (1.17.1 lines 561–566; 1.18 lines 576–580): a grounded move clears fall distance, and otherwise a negative resolved vertical displacement adds its magnitude (1.17.1 `Entity.java` lines 998–1010; 1.18 lines 969–981). Later movement/tick writers and collision-specific outcomes are outside this bounded audit. No source-generation, Git, implementation, wiki, test or game-launch action was performed.
