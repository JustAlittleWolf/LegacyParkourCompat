# Accepted local jump and Elytra pitch evidence

This archive preserves one exact grounded ability-flight activation witness and two source-accepted Elytra pitch-lift comparisons. The first shows a source difference in the same-tick movement sequence; the other comparisons are scoped pitch-factor differences for an already-active ordinary dry local glide. These are source-evidence records, not runtime-parity claims or full movement coverage.

## Grounded ability-flight activation: 1.20.4→26.2

The blind audit accepts the reachable second-tap case. A first rising press during the final falling tick starts the 7-tick trigger; after release and its tick decrement, a second rising press while grounded toggles ability flight. 1.20.4 does not call `jumpFromGround()` at the toggle site; its later ordinary grounded jump overwrites the flight-up contribution. In 26.2 the toggle first calls `jumpFromGround()`, then the held-jump flight acceleration is added, and the later ordinary jump uses `max(jumpPower, currentY)`, preserving it. The audited witness distinguishes post-travel restored velocity from actual displacement and does not claim runtime parity.

- Report SHA-256: `10e54c69c6126af5d7ddc860c5eec4e95549cf1708e9478630df3ff4f07428e6`
- Strictly blind verdict SHA-256: `fed7ce0b875e2cb6550fda7edc0957a03d66ef292e11b8164c332ea2d21f22ab`
- Read-only baseline association at `aae9c86464638cb5ed7faa535c92569b89484906`: [FlightActivationJump.java](../../../../../src/client/java/me/wolfii/legacyparkourcompat/change/v1_20_2/FlightActivationJump.java), SHA-256 `9ac9cbad2ea2ddfd86b3b9daec6fc06bbff27e0c704333178faf591c69714a41`, is a no-op for the 1.20.4 activation call through the 1.20.4 alias range. The ordinary jump remains a separate operation. 26.2 is outside this no-op behavior.

## Elytra pitch-lift factor: 1.18.1→1.18.2

The original report and independent audit are preserved unchanged with the signed correction and acceptance. The correction narrows the changed factor to the gravity and downward-velocity terms; the separate negative-pitch `Mth.sin` pull-up term is unchanged. It also corrects the unit-look approximation and uses the exact `0.98F` damping when reporting witness values. The accepted result is source-only for the active dry local-player path through its first real self movement call.

- Report SHA-256: `4e5094d335e3f6082cba82ca2a7d9987d48662390a97f4368699b8a6cf56b3ea`
- Independent verdict SHA-256: `615a282617c3aea6258e1f013eddfd5090d1e0106183c7dfbd01ea404f9bab5d`
- Correction SHA-256: `bb7872c10512c1b4ab34d57ff6a51ece783a44cc6ba351ce22bc6981fc7a4db0`
- Correction acceptance SHA-256: `1f734b71c601a1e5c603ba2dba4ddf983118c8664d088eaeaa4714dcad10dd5c`
- Baseline product references at `aae9c86464638cb5ed7faa535c92569b89484906`: [1.18 lift provider](../../../../../src/client/java/me/wolfii/legacyparkourcompat/change/v1_18/ElytraLiftForce.java), SHA-256 `fb1c388a597e8baa5b930b2ce6a116c8df48850a693d9cc33e498c90120d3f9f`, and [1.18.2 lift provider](../../../../../src/client/java/me/wolfii/legacyparkourcompat/change/v1_18_2/ElytraLiftForce.java), SHA-256 `04e1e6bbb7659b19a007244a923c0964ec9c69cd8a5124eaaf9b09bcf0243720`.

## Exact destination witness: 1.18.1→26.2

The separate destination report and verdict accept the endpoint source difference in the pitch-derived lift factor for already-active ordinary dry local fall-flying. 1.18.1 uses the float lookup cosine, squares it, and applies the capped look-length ratio before narrowing; 26.2 uses `Math.cos(leanAngle)` in double and squares it without that cap. This is not a patch-introduction boundary or a full-pair result. The separate sine-derived pitch-up term is not included in the changed-factor claim.

- Report SHA-256: `0300468d76447b7ded545ab512c14f5fee46ccdcbde6ffd0132a6ef3ef075327`
- Independent verdict SHA-256: `85e23dd1f74b97d7d4236061a23b8426f12e70d937fa9444059f8bb8b043c4de`
- Existing float look provider through 1.21.10: [FloatTableFallFlyingLook.java](../../../../../src/client/java/me/wolfii/legacyparkourcompat/change/v1_21_10/FloatTableFallFlyingLook.java), SHA-256 `c100b36801fc2dd18277b9d13ae2e36e6c4c855917e37eff58afcfdfd8c039ad`. Its comment bounds that provider at 1.21.10; the 26.2 native double path remains outside that provider.

Every report, verdict, correction and acceptance file is copied byte-for-byte. No source generation, implementation, backlog edits, tests, builds or runtime checks were performed.