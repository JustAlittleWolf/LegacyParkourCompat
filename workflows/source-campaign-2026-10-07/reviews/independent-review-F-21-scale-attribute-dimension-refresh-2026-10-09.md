# Independent source review: F-21 scale attribute dimension refresh

- Review date: 2026-10-09
- Decision: **REQUEST CHANGES** (candidate not accepted; revision required)
- Candidate: commit `a6a244d2e46b57c512b8d4c1508e955b667397b9`, `workflows/source-campaign-2026-10-07/1.21.4--1.21.5/findings/F-21-scale-attribute-dimension-refresh.md`
- Candidate Git blob: `7e62e675d1870cdb4cff352f8f5182d683a1d16b`
- Candidate raw SHA-256: `5c9eb375c6a88c45979b03863b2287581844435727d0066ebb9bd84aa7308a34`
- Source-owner correction reviewed: commit `c80f0292773bedf3b3c687bf24edcc6cac5f3c84`, pair `run.md` section “F-21 snapshot correction — invalidated before independent review”. The correction leaves the candidate finding unchanged and marks its snapshot invalidated/withdrawn.
- Pair source manifests: 1.21.4 `f90b61197928632e061ea877955a19055c92ae6f357c2daf1bc646172c6f51f0`; 1.21.5 `365cc2d22446ceba0e36f46aa1c95ce4cc31514dbd657505680fb660f3adefd9`. Published cache root: `build/movement-campaign-2026-10-07/ready/`.
- Scope: finding-snapshot review only. Pair remains partial; runtime validation was not performed.

## Evidence and ordering

The immutable finding correctly identifies a source change in `LivingEntity#onAttributeUpdated`: A (1.21.4, `LivingEntity.java` SHA-256 `e62ce650af0a5ac97e4d0a2ba7cad4ba68a8d098525609f8b5b6976a8ab0ae30`) handles maximum health and absorption at lines 1058-1071; B (1.21.5, SHA-256 `a8aed863d4fdc515c751dd2878a8bb9c13179228cb8dbb50edf1d19cd5404271`) adds the `Attributes.SCALE` case and calls `refreshDimensions()` at lines 1077-1091. Player inherits the scale attribute through `Player#createAttributes` extending `LivingEntity#createLivingAttributes` (A `Player.java` SHA-256 `c45f41b9784ce50a88a498e84a13edcef0a23e175233d726ed7f90940f19ebc1`, lines 229-242; B SHA-256 `8fc187f33999db9dfc49251e95e92a17645a50adab16ca0f93f4948209f62036`, lines 244-257). `getDimensions(pose)` scales non-sleeping dimensions using live `getScale()` in both releases (A `LivingEntity.java` lines 3352-3358; B lines 3343-3349). The paired `Entity#refreshDimensions` stores those dimensions and eye height and reapplies position (A `Entity.java` SHA-256 `05f18ef2ec0413fc010230407c812a11553eb5123b68d21b5d7b2c0c175698ad`, lines 2948-2964; B SHA-256 `fc177733e9cc4b2d5cf2562e5529d0f4e0b9c3690f081fbd58aa9119eb173dc4`, lines 2941-2957).

The candidate’s reachable base-value mutation uses the vanilla attribute command’s `setBaseValue` path; the resulting dirty attribute is dispatched by `refreshDirtyAttributes()` to `onAttributeUpdated`. For the stated command path, the tick ordering matters:

- In both versions `LivingEntity#tick()` invokes `aiStep()` before its final scale/dimension handling (A lines 2429-2470; B lines 2460-2501).
- In A, the tick tail calls `refreshDirtyAttributes()` at line 2550, then reads `getScale()`, compares it with `appliedScale`, stores the new value, and calls `refreshDimensions()` when it differs at lines 2551-2555.
- In B, the tick tail calls `refreshDirtyAttributes()` at line 2570; the added scale callback refreshes dimensions while that dirty set is dispatched (lines 1088-1090). Its old applied-scale poll is absent.

Thus the callback source difference is real, but the candidate’s submitted command-to-next-tick path does not leave A with stale dimensions for a subsequent movement tick: A’s poll refreshes dimensions immediately after the dirty set is processed, still after that tick’s `aiStep()` and before the next tick’s movement. This masks the claimed stale-A/refreshed-B collision or eye-height movement consequence for that path. The candidate itself also says “no trajectory is claimed”; the issue is that it claims a movement-relevant state divergence without closing this existing refresh path.

## Disposition

Uphold the source owner’s invalidation of this candidate snapshot. Do not treat the callback-only source delta as an accepted movement finding. The movement consequence is withdrawn and requires revision before acceptance.

A narrower timing case remains open: `onEffectUpdated` and `onEffectsRemoved` can flush dirty attributes through `refreshDirtyAttributes()` outside the tick-tail poll (A lines 1017-1045; B lines 1035-1065). The record does not establish that a pending player scale mutation reaches one of those flushes in a way that produces movement before A’s later poll, nor does it close other earlier refresh paths. This review does not create a replacement finding; any revised candidate must bind that player-reachable producer/consumer ordering and its bounded movement consequence to exact sources.

No implementation, wiki, MCPK, runtime, or full-pair coverage claims are made.
