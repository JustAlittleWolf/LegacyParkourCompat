# Independent audit: direct player response producer ordering correction

Verdict: **ACCEPT** for the bounded correction.

## Bound inputs

- Correction report: `build/source-early-patches-20261117/1.11--1.11.1/responses/direct-player-response-producers-r2.md`
- Correction SHA-256: `39A493F74452CF591B833DEBA802E123DC09673D54B4C626E9E7F0D052A74C66` (matches assigned identity).
- Original report: `build/source-early-patches-20261117/1.11--1.11.1/responses/direct-player-response-producers.md`, SHA-256 `D3284ED0878E872E64DB750F43D480C9E9D137B0DC229A1F81E06C17F0120EB3`.
- Original audit verdict: `build/source-early-patches-20261117/1.11--1.11.1/responses/direct-player-response-producers-audit/verdict.md`, SHA-256 `140F34B8C5269028EFDCCC03DCCB18BF8CAB414E366A3E28300D58E36CD2B122`.

## Accepted correction

The r2 text accurately corrects the prior branch-order statement. In 1.11.1, the sleeping branch immediately follows calculation of `q`; its strict `q > 1.0` correction check precedes and bypasses the moved-too-quick `q - p` check, which is inside the non-sleeping `else`. In 1.11, no sleeping split exists at this location, so the moved-too-quick check applies to both sleeping and non-sleeping players, subject to its existing gates. The corrected gate description retains the preceding invalid-packet, pending-teleport, and riding control flow, and does not characterize server movement processing as historical client physics.

The correction explicitly preserves the original report's pair/source identities, packet construction, recipient, receiver binding, exclusions, and partial-coverage limits. It adds no unsupported response or pair-closure claim. This verdict accepts only the ordering correction bound to the cited original report and REQUEST CHANGES verdict; it does not replace or mutate that verdict and does not constitute a full source re-audit, pair freeze, implementation readiness, or runtime acceptance.