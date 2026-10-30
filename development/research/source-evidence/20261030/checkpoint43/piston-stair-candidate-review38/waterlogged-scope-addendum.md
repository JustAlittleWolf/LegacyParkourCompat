# Scope addendum: modern waterlogged stair fallback

**Reviewed correction:** root product commit `80f9d713` (integrated into the current branch at `6b8b171c52ebcaeffd34c9fcd057c10434cdee8a`).

**Verdict: ACCEPT for the narrow domain correction.** `CobblestoneStairPush.requestedDistance` now returns `OptionalDouble.empty()` for retracting movers and for moved states whose `WATERLOGGED` property is true. The latter leaves the mixin's accumulator and native per-shape gate untouched, so the modern-only waterlogged state keeps its native response. The guard applies only where the property exists; ordinary unwaterlogged cobblestone stairs retain the already-reviewed legacy response. This closes the accepted-domain gap without expanding the historical source claim or imposing broader state-provider parity requirements.

Static source inspection only; no additional runtime claim. The root reports a successful build separately.
