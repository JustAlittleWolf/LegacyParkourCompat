# Exact release chain: retained fall-flying through swimming pose selection

Status: COMPLETED for source predicates, caller order, desired-pose selection, and fit gates across every exact release listed below. Source-only. No runtime trajectory or collision-free world route was tested.

## Bounded result

For every exact release from 1.14.0 through 1.21.4, inclusive, the source chain admits the same conditional overlap: an airborne local player can enter swimming while the independent fall-flying state remains valid; the player desired-pose selector evaluates FALL_FLYING ahead of SWIMMING; and the selected pose is applied only if its fit check succeeds. Where the selector has a preliminary swimming-fit gate, that gate must pass before the priority branches are reached. Desired-pose selection is distinct from this fit admission.

The earlier state writers do not reject water. `LivingEntity` validates retained fall-flying from airborne / not riding / usable Elytra conditions (plus Levitation absence from 1.16 onward), and writes the shared flag only server-side. Water entry resets fall distance but does not clear that flag. Swimming admission requires sprinting, appropriate water state, and not riding; the Player override suppresses it for ability flight, not fall-flying. The route is conditional on these gates and on both pose fit checks.

This proves a source-level reachable overlap path, not a particular world-space trajectory, remote-server synchronization schedule, or runtime tick trace. Sleeping, spectator, riding, ability flight, invalid Elytra, Levitation (from 1.16), and failed fit are excluded.

## Exact release coverage and source authentication

Coverage is contiguous: **1.14, 1.14.1, 1.14.2, 1.14.3, 1.14.4; 1.15, 1.15.1, 1.15.2; 1.16, 1.16.1, 1.16.2, 1.16.3, 1.16.4, 1.16.5; 1.17, 1.17.1; 1.18, 1.18.1, 1.18.2; 1.19, 1.19.1, 1.19.2, 1.19.3, 1.19.4; 1.20, 1.20.1, 1.20.2, 1.20.3, 1.20.4, 1.20.5, 1.20.6; 1.21, 1.21.1, 1.21.2, 1.21.3, 1.21.4.**

For Mojmap releases, the fingerprint pass covered every release in that list with canonical class/method bodies for Player pose selection, Player swimming update and predicate, Entity swimming update, LivingEntity fall-flying continuation, and Player start helper where present. Hashes below distinguish unique normalized bodies; exact release membership is stated per body group. The Mojmap source tree manifest/ready metadata was checked on ready-marked folders; specific full-source authentication records for 1.21.0/.1 and 1.21.2/.3 are in the adjacent immutable proof files. 1.14.1–1.14.3 are Yarn-only here, so their nested `.decompile-provenance.json` records pin exact official client jar, version metadata, Intermediary, and Yarn inputs; relevant `Entity.java`, `LivingEntity.java`, and `PlayerEntity.java` digests were matched to their provenance `sources` entries. Exact client object SHA values: 1.14.1 `7194d1326cf796f62ac55f3cb56f851c232895639ae27510f3207d5a73f91814`; 1.14.2 `9b740c9fd4955981125ebd3cd086baa8b4392963d043a3480b5b942a96637c25`; 1.14.3 `4063062677433988e012fba53e42e4c21adb6919ac7f061f19f3c6e75b334e07`. These exact 1.14.1–.3 source-file SHA-256 values are: 1.14.1 Entity `ce429699f2d8f0a54f4be516783a474c6c7808572950164508ceed0baf68ed5b`, LivingEntity `331e2d7dfa936a6f7197910ba374c4a60b479c8565ff7c64a4d6a9e1e8b52dda`, PlayerEntity `ebda4ad25a265f8c85e88345efcce753b78e248a29a9b910c5cfd311a4a69319`; 1.14.2 Entity `7c0e9cab0842a45e42fa9f27c9661de3fd0fd076c35ff989aa0477198eb38edc`, LivingEntity `e8224e7df98ccf0986bb390f35e64293263d17b9ab1dbdfeb661c65d28b6ee78`, PlayerEntity `333bb39db18af4cef320eb73b7abe359d438fb2a6ca7d8798ee449da6c684398`; 1.14.3 Entity `9b204a5f9af6ca50c09db4b6185ff37c39980c1a8cd1b5fd004713ecffae5c14`, LivingEntity `e8aed9c63c0dfbe88e4b9e11c58881bc6e04b9cbaecbde5e3606e3afa406d72b`, PlayerEntity `e3f361ac4e369d01473f3d84044ee33f9685d182c4485e2e80b7f62756950963`.

## Predicate and ordering boundaries

- **1.14–1.14.3 (Feather / Yarn):** `PlayerEntity.updatePlayerPose` first asks whether SWIMMING fits, then chooses fall-flying before sleeping before swimming; it separately fits the chosen pose and otherwise falls back to sneaking/swimming. 1.14.1/.2 use Yarn's intermediary enum names (`field_18077` fall-flying, `field_18078` sleeping, `field_18079` swimming); 1.14.3 uses mapped names. Do not infer names from the intermediary identifiers. `Entity.updateSwimming` and `LivingEntity` flag validation retain the gates described above. `PlayerEntity.updateSwimming` only suppresses ability-flight.
- **1.14.4 → 1.15:** the fall-flying START packet check moves from inline server-handler predicates to `Player.tryToStartFallFlying`. The new helper no longer requires negative vertical velocity. The target case already begins falling dry, so this caller difference does not close this route. The independent retained-state writer remains water-agnostic.
- **1.15.0–1.15.2:** selector ordering and fit checks persist. Start and continuation gates remain airborne, not riding/already flying as appropriate, dry for start, and usable Elytra; neither water-entry nor continuation adds a water disqualifier.
- **1.15.2 → 1.16:** Levitation becomes an explicit disqualifier in start and continuation validation. Applicable overlap therefore requires no Levitation from 1.16 onward. Selector and fit ordering persist.
- **1.16–1.17.0:** selector ordering and fit persist. Swimming's fresh-entry gate is sprint + underwater + not riding. `Player.updateSwimming` continues to suppress only ability flight.
- **1.17.0 → 1.17.1:** fresh swimming entry adds water at `blockPosition`; sufficiently submerged water can satisfy it. Already-swimming branch remains sprint + in-water + not riding. No fall-flying gate is added.
- **1.17.1–1.20.1:** the block-position-water admission remains. Fall-flying-before-swimming selection remains. At 1.20.1 and earlier, the selector's fit is `Entity.canEnterPose`, testing `noCollision` on the pose AABB deflated by `1.0E-7`.
- **1.20.1 → 1.20.2:** the Player selector moves to `canPlayerFitWithinBlocksAndEntitiesWhen`; it tests `noCollision` on `getDimensions(pose).makeBoundingBox(position()).deflate(1.0E-7)`. The centered-width, feet-origin AABB builder preserves the prior gate. Priority remains unchanged.
- **1.20.2–1.21.4:** selector and fit remain stable, and fresh swimming admission still requires water at blockPosition. Fall-flying continuation has no water rejection. The exact 1.21.0/.1 and 1.21.2/.3 adjacent-source alias proofs audit all directly relevant state/pose/caller classes and establish no disqualifying water-entry writer in those aliases.

## Normalized Mojmap method-body identities

These identify every distinct body across the exact Mojmap chain; a matching digest is an exact normalized-body alias for that method. Direct source inspections at the listed gate transitions interpret the semantic differences.

| Method | Body digest | Exact releases in group |
|---|---|---|
| `Player.updatePlayerPose` | `36681b1a705237de13d3b3b336185cfd9a766935c572b9c7c617aa7f54e2d6ac` | 1.14.4 |
| same | `15edef9dddbf391178c733a95a827292397756bbc2b3fb33b3deff544c6634ed` | 1.15–1.17.1 inclusive |
| same | `320ccb755b1732b55d6799e37984ec5525b4f1afc3aaf78c3350d98e07734c9d` | 1.18–1.20.1 inclusive |
| same | `89932c89462564e95d05bd9bce3f07ab1b3b886b75a0d3a0fdb851bc0662a4da` | 1.20.2–1.21.4 inclusive |
| `Player.updateSwimming` | `c909ed7792941016bfaf1c3fdbca726c98d5d14bfcf1669e829a1d90533b9dcf` | 1.14.4–1.21.4 inclusive |
| `Player.isSwimming` | `d85ea42eda8d464f55e1ab0af898a4ed75d3ac0062cc962998eab9a8e8f9d32a` | 1.14.4–1.21.4 inclusive |
| `Entity.updateSwimming` | `bbace8807456fec2cd6ff90842df739cf65d770ab06cf6e76c0460b9ca521a10` | 1.14.4–1.17 inclusive |
| same | `3979e88b7005f76e937f1fc7af9c4a5d7cebbeb4f5fb3306e9411c816f83ba92` | 1.17.1–1.19.4 inclusive |
| same | `0b8ebb5a0274bbfbdcad5127f17e020b55729d82035bfc092cacf1039abcf842` | 1.20–1.21.4 inclusive |
| `LivingEntity.updateFallFlying` / corresponding continuation body | `c130ad64c610ce42a0a071ecd2fde38c0af96f9d9006b2151e40cfbaaac6838d` | 1.14.4–1.15.2 inclusive |
| same | `c940a2b2190d039f6520638c068c9260f2fc24fb742e2087399ea09958d69a37` | 1.16–1.16.5 inclusive |
| same | `fd8581eb5e37b00d29efcfc6a07ac0ea189065e1bf27cffb21ffea500d2ed70b` | 1.17–1.17.1 |
| same | `b88969a0f73bfd411383eb8273107ebac836fe8ed12e0e784eeb03b8711c83c9` | 1.18–1.18.2 |
| same | `89766ff942dd123188da91d485a9f9d9507aebb6c0066c69ed1ca0ea6f4c07ae` | 1.19–1.19.4 |
| same | `58f430c65284827971cbcef7a312139b8ace426baca55b9e6cfec342f3d01f37` | 1.20–1.20.4 |
| same | `bc00c1b2583c5e16cc4aaa41f76214a279bd7dcd7043fb53125490ec2942d765` | 1.20.5–1.21.1 |
| same | `1dd1d4e3c3a6399f56358223751d84a46244e180e327b0aab3558dc0a3c153d8` | 1.21.2–1.21.4 |
| `Player.tryToStartFallFlying` | `5aa1444f9d531f885412e5f388aaaa033cd0252f30dcc18c9e5df9c4bbccc` | 1.15–1.15.2 |
| same | `899a75b38dcd4d5b88f080a29430d9d6c015620b2e0df6381610ff4998ec8473` | 1.16–1.16.5 |
| same | `a3e3b439c21c404aced70ee663e3f63f651f85c927384cfa145b48b7c0bddaaa` | 1.17–1.17.1 |
| same | `dd6ec48d1a1827019947cf34814558f85b784bbdbf6eefbe5be26366c72b20b2` | 1.18–1.21.1 |
| same | `23dbc07541fe451708ac9c438cee39f644b4ca0101eb162514402b9d28a105ad` | 1.21.2–1.21.4 |

## Explicit limits

The earliest exact desired-pose selector established in this lane is 1.14; the earlier 1.13.2 evidence is a competing-dimensions selector rather than distinct Pose priority. This artifact does not extend before 1.14, prove the first exact 1.13 patch with the behavior, inspect generalized Elytra equations, or verify a physical trajectory. Releases after 1.21.4 are outside this bounded chain and include the known 1.21.5 selector reversal from the frozen input.