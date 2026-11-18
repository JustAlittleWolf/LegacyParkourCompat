# Independent audit verdict: 1.17.0 → 1.17.1 ability-flight Elytra travel

**Verdict:** ACCEPT the bounded no-difference claim in the frozen source report. This accepts only the audited pathway; it does not close pair coverage, establish full-pair freeze, or establish runtime parity.

## Frozen claim and audited scope

- Source report: `c-mid-117-1171-ability-flight-glide-clear-source/report.md`
- Frozen report SHA-256 (verified before opening): `39d36f206d8a37276b7debf5a13af776743426d3572955f9313bfa938c198653`
- Exact scope: ordinary dry airborne local player; not a passenger; lawful ability flight enabled; fall-flying active with default chest-slot Elytra; `Player.travel(Vec3)` through inherited `LivingEntity.travel(Vec3)`; post-real-move player wrapper state writes to shared bit 7 and `fallDistance`.
- Release/mapping pair: 1.17.0 source ID `1.17`, Mojmap → 1.17.1, Mojmap.

## Independent source verdict

The complete `Player.travel(Vec3)` slices are byte-for-byte equal as decoded source text after aligning source offsets: 1.17.0 lines 1418–1448 and 1.17.1 lines 1409–1439. In both, the `abilities.flying && !isPassenger()` branch saves incoming vertical velocity and flying speed, updates flying speed, calls `super.travel(vec3)`, restores vertical speed to saved `y * 0.6`, restores flying speed, writes `fallDistance = 0.0F`, then calls `setSharedFlag(7, false)`; the native `else` fallback and movement-statistics call remain in the same order.

The `LivingEntity.travel(Vec3)` fall-flying branch slices are also byte-for-byte equal after aligning offsets: 1.17.0 lines 2064–2106 and 1.17.1 lines 2068–2110. The glide branch may write `fallDistance = 1.0F` when incoming `y > -0.5`; after the inherited call returns, the player wrapper overwrites that separate state with `0.0F` and directly clears shared flag 7. Under the assigned dry/airborne preconditions, the earlier fluid branches do not supersede the fall-flying branch. `isFallFlying()` returns shared flag 7 in both versions (1.17.0 lines 3009–3011; 1.17.1 lines 3013–3015). The chest Elytra and lawful ability assumptions are scope preconditions, not claims about the separate Elytra-start eligibility path.

No operation-order, gate, numeric, cast, movement-call, direct-flag-clear, or fall-distance difference was found in this bounded pathway. The frozen report's state-write distinction and no-difference conclusion are correct.

## Evidence identity checks

- 1.17.0 canonical mapping root: `decompiled_minecraft/1.17/mojmap/`; hidden `.decompile-provenance.json` SHA-256 `afc9dc30cd8f6406d8ea306e64cc0b3601cc8d7c7bcd314f737573d830a79357`. Its pinned source hashes match the two source files below. Its source inputs are pinned and the cached `client.jar`, `client_mappings.txt`, and `version.json` each match their adjacent `.pin.json` SHA-256.
- 1.17.0 `Player.java`: SHA-256 `9ad4c439814caf5f51cf6da9a86c5d22906b0a28b5b606bdeb1b770617d90516`; `LivingEntity.java`: `8e81e5f1317d154fa41b676721ff581badd46296f77bcebcadb3c63448e528ed`.
- 1.17.1 ready marker identifies exact `1.17.1` Mojmap output. Its source-manifest SHA-256 matches the marker (`93270d229acfb751bf56daf1e7be26ce3dcff26b29e94aa157de621405a3463b`); its artifact-manifest SHA-256 also matches (`e52c5dbae7d9663190ccc55a4f9b44a8e0615fb1fbbd8280811df43f70ec8aaa`). The manifest entries for `Player.java` and `LivingEntity.java` match the files below.
- 1.17.1 `Player.java`: SHA-256 `724bb298499dabe489dffd5ce7ec81c9773619a8d2c70044911eae4c9e8ff481`; `LivingEntity.java`: `33fd081aadb2b6fdc9ebf487db6da5b38c54f4b8676572790ee2203690d15e6f`.

## Limits

This verdict independently checks only the frozen claim and the stated direct state writes. It does not audit the complete tick/inventory graph, Elytra activation/eligibility, other airborne branches, any intermediate patch boundary, product code, or execution behavior. The source report is a bounded no-difference result, not full-pair acceptance.
