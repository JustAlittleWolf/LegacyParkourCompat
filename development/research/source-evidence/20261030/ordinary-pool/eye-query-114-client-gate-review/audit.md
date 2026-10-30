# Independent audit — client loaded-check override

**Verdict: pass for the substantive correction.** The supplement is unchanged and its SHA-256 is `f9f028bec16e1a2d947726734d6aa328ab8d021fdb6f900dc5cb59a47ad70319`.

## Findings

- In 1.14–1.14.4, the eye-query helper calls `this.world.isChunkLoaded(...)` through the world instance after computing the eye block coordinates and before `getFluidState`. `ClientWorld` overrides that virtual method (`@Override`) to return `true` unconditionally in every inspected release: 1.14–1.14.2 lines 212–214 (source SHA-256 `86b4b5451115fde93bc1f6546dd07c93a373b09232870c0721f524216556368f`), 1.14.3 lines 213–215 (`9c72e6f4d142b5ccbe33f4f33e6593705fd7e02755275f0f8a330561463117e2`), and 1.14.4 lines 222–224 (`c4674cc7c6b322bef6a5a951aa73be2b405de4bc64804fb9b6bb96c1832b1768`). Thus the `true` argument cannot reject the ordinary client refresh for chunk residency; the query proceeds to its fluid-state and height checks.
- Confirmed those source hashes against the four Yarn `.decompile-provenance.json` rows and the 1.14.4 `ornithe-feather.sources.sha256` row. The Yarn provenance-file hashes are respectively `ea70a955a12f377cd3b9e8d5f346f4625b044d55da726b6bc472405ae91f4341`, `308537c5adec3104c5ca243eb5010554f00978635ed74f0d7801c804dd4d4907`, `786546f99684423cc2563be45c9c3334dfcc2c7db58ddb74ba39ce403bd0869a`, and `81ea0021eb122d4e3340e20d47d6ecb7079365ba767a038d26a8264cddacc28a`. The 1.14.4 manifest hash is `717b468536348557f9dae8cf57edccf80ad16eb65e5bef6c60bdb5ea3c9f75bc`.
- The 26.2 comparison is also accurate: `ClientLevel.hasChunk(int,int)` returns `true` (lines 517–520; source SHA-256 `f0255138128f19fb354e1a208f457e7fdb2df986f3103ec91f52bcabfa076631`), matching the `unobfuscated.sources.sha256` row under manifest SHA-256 `a7ad74fc712567eb87136afc1eafcfd602b4088e9869f3596331fd34faefe894`. This stub answer does not remove the separate entity-fluid interaction preflight described in the bound source review.
- The query sources support the correction's applicability: the sampled eye chunk is passed to the virtual call before fluid lookup, so the previous absent-adjacent-chunk rejection witness does not apply to this client path. This does not alter the contextual-height operation: the same query still reaches `getHeight(world, pos)` when the fluid tag matches. The separate contextual-height conclusion remains the bound source review's finding; this gate audit does not re-audit the height producer.

## Precision note and open gate

The supplement's opening phrase `Entity.isSubmergedIn(tag, true)` in 1.14.x is not the exact symbol throughout that range: 1.14–1.14.2 name the helper `Entity.isInFluid(tag, boolean)`, 1.14.3 names it `isSubmergedIn`, and 1.14.4 uses `m_69330161(tag, boolean)`. The client override and corrected applicability conclusion are unaffected; treat the phrase as a family-level description, not a release-exact method name.

No open gate for the requested client-override correction. This audit is limited to the inspected 1.14–1.14.4 and 26.2 sources; it makes no broader version-boundary or runtime-parity claim.
