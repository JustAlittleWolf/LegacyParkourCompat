# Elytra negative-pitch look guard: exact-source applicability audit

Status: immutable audit input for the bounded guard/applicability claim. This file records source identities and calculations only; it is not a product implementation or full-pair coverage record.

## Scope and upstream accepted evidence

- Accepted finding: `1.12.2--1.13.2/elytra-look-guard.md`, Git blob `c995ac0ef4e8d265ae1c0675cad15dd0ed9496b1` at commit `583f8a9b`.
- Accepted 1.12.2 → 1.13 numerical witness: report blob `b779c8861057b798c5f9ec65dd8a417b568fb086` and independent verdict blob `92758224c2a6dcdae79607428de566566715a27a`, at commit `b8ca33c5`.
- Reused witness’s scope: ordinary local client, dry, already-fall-flying state; camera pitch `-90.0F`, yaw `0.0F`; no Elytra startup claim, trajectory claim, or reachable old divide-by-zero claim.
- This audit checks exact Feather-mapped local sources for every release from 1.9 through 1.13.2 present in the canonical tree, focusing on `LivingEntity.moveRelative`, `Entity.updateLocalPlayerCamera`, `Entity.getRotationVector(float,float)`, and `MathHelper` trig lookup. No wiki, product implementation, generated source, test, game, or runtime harness was used.

## Per-release raw input allowlist

All entries use the `ornithe-feather` source namespace and Feather Gen2 build 2. Client and mapping hashes are SHA-256. The listed exact-version release ID and artifact pair must be used together; do not substitute a neighboring patch release.

| Minecraft release | Raw `client.jar` SHA-256 | Feather mergedv2 mapping artifact | Mapping JAR SHA-256 |
|---|---|---|---|
| 1.9 | `0273b5c79d6b96799c6480ae61366d6b1d042655970de87f77f1a3f1ebea9307` | `feather-gen2-1.9+build.2-mergedv2.jar` | `a4e09a92eab0fbf6ef4668e295db1949e9d52c42f145d56b6dfbf3a10c2c7c2a` |
| 1.9.1 | `56ad7e53f6f4ad393844e274aa2190692b61d2ad9f6b3d12b33a8b7332fd51f9` | `feather-gen2-1.9.1+build.2-mergedv2.jar` | `6d5768353bf2ff2756d38537312548a8ac91d570f130d23c23e9412d2edd97b3` |
| 1.9.2 | `ef87eb71250df1b871deab8d3ef10aac3290b9e78667ad548fde4d0e14af0d65` | `feather-gen2-1.9.2+build.2-mergedv2.jar` | `e2b98ececbff8fa0bd0c89283bbb32b7f9f9dcd4b89fa3f137666fa3ac0b6859` |
| 1.9.3 | `ab8fdde789c5b4614ffc98249e04fdcce7dfce320b55215bfe5418608af6a63b` | `feather-gen2-1.9.3+build.2-mergedv2.jar` | `fc5defa1546cf87888a11783e2b5d9b48b47c6c022f48de4e55add978ab35da3` |
| 1.9.4 | `23e90103a1ca2ac71100004c6d5846de09f85695f579843ef8da41571e60c908` | `feather-gen2-1.9.4+build.2-mergedv2.jar` | `49a38d0adfbda1749e519c29844116e9f22e895cb505633261b7f587268f4125` |
| 1.10 | `c00f8170ebf4fd834f552246743a64885a6dffb3dd1e3a3f3dba9676b7a0af35` | `feather-gen2-1.10+build.2-mergedv2.jar` | `ad7cf6744d81d0260c9d97ddfe8250b2fdbd54fe332d4d00266b3545d8ddf8dd` |
| 1.10.1 | `338b205f20c22b9c676d9d3227a5e6c5e190f30ad84d7caad035150b3e05ba72` | `feather-gen2-1.10.1+build.2-mergedv2.jar` | `6e8ba44832b7b77d913962462bde55084005d5de6389232453afc0bc0339ea37` |
| 1.10.2 | `7cdf7fcdc1c92584a233bf3c42bd7f0df1bdad3007d306831fe50410692be1e9` | `feather-gen2-1.10.2+build.2-mergedv2.jar` | `18cffc56c2d8de89b50cb0328b174566236553c4aafb62afdc58a1e0ff0cadb0` |
| 1.11 | `f0d11987ea743f55a2c03bf77216e6dc9baea7201cc7bf498ceb18043e984634` | `feather-gen2-1.11+build.2-mergedv2.jar` | `658ad8c5a5d661eb5c2f97c00a47f6e02c02d5eee1eb1005f8e2926eb95dec44` |
| 1.11.1 | `eff72a31339cd9a96a61092eed2d95b95e4cc91516bea4e9105c3d73574147f6` | `feather-gen2-1.11.1+build.2-mergedv2.jar` | `83c2f49749b8eff663bf67664204e9cf02c75174e264d4cd57a6ba9f01bd2928` |
| 1.11.2 | `be3fff4f2cc005a1310a96389efdeb983d2bcb4b8e747c402acd616ae73d0ba2` | `feather-gen2-1.11.2+build.2-mergedv2.jar` | `d14500101ac23c874b0fe394eae21a382c410ec4f3bbc2e58042e5234a236757` |
| 1.12 | `59e84cf6acc3912e1705ab05c722485afb3ec33343eef4756d8c16edfe6411ca` | `feather-gen2-1.12+build.2-mergedv2.jar` | `73ce7d6d0a281db15fa030ae09e836387dae1172fdffcaf3e2d1a4840830b719` |
| 1.12.1 | `19331fb29bf8b8d5ed764677c94cca9d66481e10104f660eb555894a4300cf22` | `feather-gen2-1.12.1+build.2-mergedv2.jar` | `f94febd2167d6091b4c157aef0a85c8791e6f8fb27b883e27a82a68c7b02b9c7` |
| 1.12.2 | `8ada07da5ee77dad3527bd7278fbd05ee1fc8a597813b216a871a2d7d64cc64f` | `feather-gen2-1.12.2+build.2-mergedv2.jar` | `e48244030c53979793bdfbe48d7f1f3536f7e4f678ee5890a416198037cd46ca` |
| 1.13 | `d807a5ed9428b6c16b64888fd2b7fa76dcf5c55d06f96f7c0c47762c3fc3e26e` | `feather-gen2-1.13+build.2-mergedv2.jar` | `a7a61604c7b365db1f4250c4c0079172c3beff37f4e16f7a53a13d77f3976897` |
| 1.13.1 | `f0b4cf6d1a87ae150f87a535651138ca7b82bdad8853f76e7ddbff1dd610ac87` | `feather-gen2-1.13.1+build.2-mergedv2.jar` | `6737db17e8cc724a018aad93d7fe5051a50e77136848d78d79b62310846fa65d` |
| 1.13.2 | `3410887ba652f25792c7675bfaf9140e73b60e93cfbf113a803f8a98cb05c0f9` | `feather-gen2-1.13.2+build.2-mergedv2.jar` | `317384d4faccc2939c3b14252993d31745eb42aad59f2f0ea78893cbe4e7283b` |

## Source identity allowlist

The following SHA-256 tuples are for, in order: `net/minecraft/entity/living/LivingEntity.java`, `net/minecraft/entity/Entity.java`, `net/minecraft/util/math/MathHelper.java`. Grouping is only a byte-identity shorthand; each release still uses its own raw input and mapping row above.

| Release(s) | LivingEntity SHA-256 | Entity SHA-256 | MathHelper SHA-256 |
|---|---|---|---|
| 1.9 | `5b5936656aacc64a4d75db7b370985f9456ac96076331da047bd246f76a705fb` | `74d3ac34d7e6355c8fdcdd777dcd95ba9ef4626718c8772c1b58d0e28419e42a` | `43b2a4e3931733dbc3538024b7182b024d874a5f22a269a6199c6b4ca8c59151` |
| 1.9.1–1.9.2 | `d8824f670037a88753c428c27213989fc5173b1d7c638f498f73395c145b6fd9` | `74d3ac34d7e6355c8fdcdd777dcd95ba9ef4626718c8772c1b58d0e28419e42a` | `43b2a4e3931733dbc3538024b7182b024d874a5f22a269a6199c6b4ca8c59151` |
| 1.9.3–1.9.4 | `bbb7703f18fd5da05c4e4a43a77ea644b388e63c01d34166d308ea52054be4e5` | `bcd7fa2206bf8d7271102f6da7fe2771f96dbe22ec79dab9f2101a3e29c17ef0` | `43b2a4e3931733dbc3538024b7182b024d874a5f22a269a6199c6b4ca8c59151` |
| 1.10–1.10.2 | `d40dd476b6b68c6ce45b4202823475deb546ecda2284da330ff6724b33815e82` | `05da145effa19a6ef7934cc276e89226373b67c12f4ce89a8ce2183f29039f77` | `43b2a4e3931733dbc3538024b7182b024d874a5f22a269a6199c6b4ca8c59151` |
| 1.11 | `1f2e31fa6a905ccc2a51a0338f2aaf448f2b0569f28644e8205d864e570d9b2b` | `3209feaacd1e798b521a193eeb081d92c96881a70a18254ef24a58e911a4fd97` | `97aa2499681fdd8cee6564f777310ee396bc447fef78c2273a990e9202d9641b` |
| 1.11.1 | `bb7dc6c9e423a9568d6433d51bba12e7aee4555fbf3fb3e2b87f618382279f2f` | `5a906de1d40a4a5ba8aedc9a6a9dc704e0ed862df177cf967fb1a9454765fa96` | `97aa2499681fdd8cee6564f777310ee396bc447fef78c2273a990e9202d9641b` |
| 1.11.2 | `bb7dc6c9e423a9568d6433d51bba12e7aee4555fbf3fb3e2b87f618382279f2f` | `ce8104a17ce783df639cf9726e7b1cd0936563eaa7ba308603335bbe05d49440` | `97aa2499681fdd8cee6564f777310ee396bc447fef78c2273a990e9202d9641b` |
| 1.12–1.12.2 | `190e9ac551538e015d9e4d6c42856e5ba32b593131cf6d93895e7b29533f1ee6` | `80f091bf32166c88cf8bbd31caf72d84fa16224410733c7d2a0f00563f294a0a` | `579e05d110460f444bcac86729141545095cb5932c0f8d34c9a4dcfcd9a35e80` |
| 1.13 | `c38e95c2c13e088b03d77fc2c6b98d4edd7e6ca5aef12b187c8e3f3ea782e864` | `09514488d9055449c47e2e4bcd23b5773203ca634b7ecba2323a99d0ae016642` | `ad7615e7acf9d5ebe1d7cfc236a7b2dc7726c42a32bd46254ba29f9fc68c567f` |
| 1.13.1–1.13.2 | `bb691358c9a43c9f46e85575bf4d0a4ad671d0eb912502acc3a6a3f625e42f1c` | `1d6ec8b80f74635401745c2c027bf36555c85348ca5693764f2668363b17d269` | `2211b0cb3da7d8789a4a4df96509b95e6b1be8b222be2f530e7bd0695573bd69` |

## Exact source findings and endpoint arithmetic

### Reachable player gates and pitch orientation

For each pre-1.13 release, `LivingEntity.moveRelative` enters the Elytra branch under the inherited `isFallFlying()` predicate on the ordinary local player path, with the surrounding local-control/fluid gates satisfied. The pitch variable is `i = pitch * (float)(Math.PI / 180.0)` (the corresponding variable is `j` in 1.13+). Negative camera pitch therefore makes the old gate `i < 0.0F` true; the gate is not a positive-pitch test.

The negative correction computes `o = horizontalSpeed * -MathHelper.sin(i) * 0.04`, adjusts vertical velocity, then subtracts `look.x * o / horizontalLookLength` and `look.z * o / horizontalLookLength`. The later glide alignment block also divides by this length only under a positive-length check. In 1.13+, the negative correction is explicitly gated by `j < 0.0F && k > 0.0` before either division.

Pitch `-90.0F` is an ordinary endpoint: `Entity.updateLocalPlayerCamera` clamps local camera pitch to `[-90.0F, 90.0F]` in every checked release. Use yaw `0.0F`; assume an ordinary dry, locally controlled player already in fall-flying state. This establishes reachability of the correction on the older side and of the new guard on the newer side. It does not establish activation/startup or any full trajectory.

### Older producer: 1.9 through 1.12.2

In the older exact source family, `Entity.getRotationVector(float pitch, float yaw)` sets horizontal scale `h = -MathHelper.cos(-pitch * (float)(Math.PI / 180.0))` and returns `(g * h, i, f * h)`. At `pitch = -90.0F`, the negated pitch produces float radians `+1.5707964F`. `MathHelper.cos` uses the sine-table index `(int)(x * 10430.378F + 16384.0F) & 65535`, which selects index `32768`. That entry is initialized as `(float)Math.sin(32768 * Math.PI * 2.0 / 65536.0)`, approximately `1.2246469e-16F`, **not zero**. With yaw `0.0F`, the resulting horizontal magnitude used by `moveRelative` is approximately `1.2246468525851679e-16`, strictly positive. Since the pitch radians are negative, the old negative-pitch branch enters and divides by this positive residual magnitude.

Across all checked releases 1.9–1.12.2, the negative correction remains unguarded. Exact gate/member line references are below. The accepted 1.12.2 witness has already audited the numeric procedure and proves that an exact zero denominator is not divided through on the old camera-clamped negative-pitch interval. At `+90.0F` an old horizontal zero is possible, but `i < 0.0F` is false, so that correction is closed; this is not a reachable old divide-by-zero fix.

### New producer and guard: 1.13, 1.13.1, 1.13.2

The new rotation vector computes `f = pitch * (float)(Math.PI / 180.0)`, `j = MathHelper.cos(f)`, then returns horizontal components multiplied by `j`. At `pitch = -90.0F`, `f` is `-1.5707964F`; the exact trig lookup selects index `0`, whose initialized table value is exactly `0.0F`. Thus both horizontal components and `k = sqrt(look.x*look.x + look.z*look.z)` are exactly zero. The pitch gate `j < 0.0F` is true, but `k > 0.0` is false, so the negative-pitch correction and its divisions are skipped.

`LivingEntity.moveRelative` has the additional positive-length term in all three inspected 1.13 releases. At 1.13, its parameters are named `j` (pitch radians) and `k` (horizontal magnitude); at 1.13.1/1.13.2 the source gate is likewise `j < 0.0F && k > 0.0`.

### Per-release method locations

All paths below are relative to `decompiled_minecraft/<version>/ornithe-feather/`.

| Release | `LivingEntity.moveRelative`: negative gate line | `Entity.updateLocalPlayerCamera` line | `Entity.getRotationVector(float,float)` line |
|---|---:|---:|---:|
| 1.9 | 1313 | 283 | 1120 |
| 1.9.1 | 1317 | 283 | 1120 |
| 1.9.2 | 1317 | 283 | 1120 |
| 1.9.3 | 1326 | 286 | 1124 |
| 1.9.4 | 1326 | 286 | 1124 |
| 1.10 | 1356 | 291 | 1148 |
| 1.10.1 | 1356 | 291 | 1148 |
| 1.10.2 | 1356 | 291 | 1148 |
| 1.11 | 1410 | 300 | 1170 |
| 1.11.1 | 1412 | 304 | 1229 |
| 1.11.2 | 1412 | 304 | 1216 |
| 1.12 | 1449 | 307 | 1239 |
| 1.12.1 | 1449 | 307 | 1239 |
| 1.12.2 | 1449 | 307 | 1239 |
| 1.13 | 1489 | 318 | 1250 |
| 1.13.1 | 1508 | 318 | 1251 |
| 1.13.2 | 1508 | 318 | 1251 |

For the `MathHelper` implementation, check the release-specific source identity tuple above; table initialization and cosine lookup math are included in that allowlist.

## Provenance sidecar audit

- Every allowlisted raw client and Feather mapping JAR identity above was read from the version’s canonical local `.decompile-provenance.json` input hashes or its `artifacts.sha256` manifest. For the latter, the corresponding ready marker’s artifact-manifest SHA matched the manifest file; source manifests matched the ready marker’s source-manifest SHA, and the listed three Java file hashes matched their manifest entries.
- For releases with `.decompile-provenance.json`, the embedded source hashes were checked against local file hashes. For releases using `ornithe-feather.provenance.json` + ready/artifact/source manifests, the raw client, mapped client, mapping JAR, tiny mapping file, and relevant source file hashes were checked from those manifests where present.
- **Exact 1.12.2 note:** the accepted witness correctly reports no `.decompile-provenance.json` in that source tree. The exact `ornithe-feather.provenance.json` is run metadata and does not itself carry the detailed raw input hashes. Identity is nevertheless resolved through `ornithe-feather.ready.json`, `artifacts.sha256`, and `ornithe-feather.sources.sha256`: raw client `8ada07da5ee77dad3527bd7278fbd05ee1fc8a597813b216a871a2d7d64cc64f`, Feather mapped client `65a08f15d18c4ec2bd0f05b89dfc1ba7ea6b8280ba93ed136245f39e63ec8a2b`, source manifest SHA-256 `b8a37ccfccd2aac5f40f5e34fec85873dbdfa93a266043c103e592e4a8c949da`, and the three checked source hashes match the source-manifest entries. No mismatch was found.
- **Exact 1.13 note:** likewise no `.decompile-provenance.json`; `ornithe-feather.provenance.json`, ready marker, artifact manifest, and source manifest establish the local artifact chain. The accepted witness’s raw client is `d807a5ed9428b6c16b64888fd2b7fa76dcf5c55d06f96f7c0c47762c3fc3e26e`; mapped client is `4b91c330aaf6179f7384f70f91ebcdf72af2713cc3788fea1482c3529905a761`; mapping artifact is `a7a61604c7b365db1f4250c4c0079172c3beff37f4e16f7a53a13d77f3976897`; source manifest is `0509c2614b0bb0e616e2ebc6382f8eb5d031b5d86b834a4a6ed7fd96b6d68f1a`. No mismatch was found.
- For 1.13.1 and 1.13.2, release-specific provenance, ready, artifact, and source sidecars establish distinct raw release identities even where the three relevant source files are byte-identical across 1.13.1/1.13.2.

## Bounded conclusion

The source change is a true historical guard difference from 1.12.2 to 1.13: the same ordinary clamped negative camera pitch yields a positive, nonzero old horizontal residual but a new exact zero horizontal length, and the new code gates the correction on positive length. The guard survives unchanged in the checked 1.13.1 and 1.13.2 sources. Checked pre-1.12.2 releases, down through the 1.9 Elytra movement path, keep the old unguarded negative-pitch correction and the older look-vector producer. This audit closes only the exact guard, pitch/look producer, and ordinary local reachability claim.


