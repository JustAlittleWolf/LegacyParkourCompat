# Acceptance: 1.17 to 1.17.1 swim-pitch slice

The bounded independent no-difference claim is accepted with the source identity qualification closed by the author's original-preserving sidecar.

## Immutable bound

- Original source report `c-swim-pitch-vertical-117-1171-source/report.md`: SHA-256 `0D36637149F9F0249D7889467D3055795C338665AA6BCB20AEB48E2C9668D4F3` (rehash matches; original bytes remain intact).
- Independent audit `c-swim-pitch-vertical-117-1171-audit/verdict.md`: SHA-256 `451B4C1CB3AE4E9CBEAF45400E6DE1316683A0C58AFD84C9EE3FE6D04E4FBB18` (rehash matches).
- Corrected source identity sidecar `c-swim-pitch-vertical-117-1171-source/identity.md`: SHA-256 `A0ACA63EBB31F008A9EA51D2201E5B5D433E1999CF35C39A25F0DCDD0A1D62E7` (rehash matches the assigned identity).
- The 1.17 cached `.minecraft-decompile-cache/1.17/version.json`, its `.pin.json`, and `decompiled_minecraft/1.17/mojmap/.decompile-provenance.json` all bind the metadata URL for 1.17 to SHA-256 `537c6fa7e6ab13caa76ce2e84e4748882d36fcd5a076fa6903f894880e1df0f6`. The cached file's computed digest also matches. The identity sidecar records the original report's `...c6fa0...` transcription and supplies the correct full digest without modifying the report.

## Verdict

The source report's bounded operation claim is accepted: no difference is present from 1.17 to 1.17.1 in the player swim-pitch Y correction, its float sine LUT/index path, or its ordering into inherited local water travel and `Entity.move`. The identity sidecar resolves the sole prior qualification while preserving and identifying the original source report.

This accepts only the named operation and examined inherited path. It does not assert full-pair movement coverage, implementation, or runtime parity.
