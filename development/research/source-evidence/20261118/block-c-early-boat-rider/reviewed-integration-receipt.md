# Early boat rider reviewed integration receipt

## Product and review identities

- Product branch: `codex/c-early-boat-rider-reviewed`
- Current primary `main` ancestry: `aae9c86464638cb5ed7faa535c92569b89484906`
- Original ready receipt/pin preserved: `7d81983579d29d13376e77aa22f8a0c5d7de4aa3` / `refs/campaign-ready/c-early-boat-rider-transform`
- Citation-correction commit: `3109eea5ab316d71777db6c11951c08a03ec6b09`
- Compiled Git tree: `9dbf6954a18480e5482454e083b8cd9b3e3027bc`
- Independent static review: PASS for exact commit `3109eea5ab316d71777db6c11951c08a03ec6b09` and tree `9dbf6954a18480e5482454e083b8cd9b3e3027bc`.

The correction changes only Change comments. Placement comments now bind the Ornithe Feather 1.8 and 1.8.9 `net.minecraft.entity.vehicle.BoatEntity.updateRiderPositon()` members to the paired 1.9.4 `BoatEntity.updateRiderPositon(Entity)` member, and cite `BoatEntity.getMountHeight()` and `net.minecraft.entity.player.PlayerEntity.getRideHeight()`. The 1.8.1–1.8.9 alias comparison is identified as the evidence for the double `-0.3` provider. The orientation comment cites the paired 1.8.9/1.9.4 placement members and the 1.9.4 `BoatEntity.copyEntityData(Entity)` helper. All references specify the Ornithe Feather mapping family.

## Source acceptance

- 1.8.9–1.9.4 placement/orientation comparison SHA-256: `2e120c8d3517ad4d229a82464397623cc7ae5601a3e9a77196c2a903adb4e3b4`.
- Independent 1.8.9–1.9.4 verdict SHA-256: `b10c9857e6689e8bc95ea4f255911bcc317f175611c13dd688d3aae2260484fd`.
- 1.8–1.8.9 placement alias comparison SHA-256: `984e133434562f5b3c7f063c0684dfe9bee022b112313dc02b7762af59799314`.
- Independent 1.8–1.8.9 alias verdict SHA-256: `3b79bdd8bd7b45cfcc9acdc82de03e02d9fcb33cbc00a59e99ae759250672f8d`.

The integrated scope remains ordinary default vanilla oak-boat, sole direct local-player placement/orientation. It preserves native fallback for other carriers and witnesses; there is no later orientation provider, and the pre-existing placement provider supplies the later resolver fence. Boat motion, steering, collision and non-player passenger behavior remain outside this finding. Runtime parity is unverified.

## Build result

- Exact command: `gradlew.bat build -x test --init-script development/disable-tests.gradle --console=plain`
- Result: `BUILD SUCCESSFUL`; 19 actionable tasks, 4 executed and 15 up-to-date. The init script disabled every Gradle `Test` task and the command supplied `-x test`.
- Build ran on citation-correction commit `3109eea5ab316d71777db6c11951c08a03ec6b09`, tree `9dbf6954a18480e5482454e083b8cd9b3e3027bc`.
- Compiled class SHA-256 values:
  - `v1_8/BoatRiderPlacement.class`: `17AAA74C633578A3F55C12ED2DCABAE9F5DA3BEDBD46F6A8FB3679C6DA51CADF`
  - `v1_8_9/BoatRiderPlacement.class`: `7E290AF3D7B66E38EFB71265E5548390E0D6B867F8BC616D544317925451EC10`
  - `v1_8_9/BoatRiderOrientation.class`: `91AE5E360DC64D1EF5DFE8B1A349FC8E7852BFC4308E0D1945A947764547FE6A`

Intended central finding association: `development/research/findings/1.8.9--1.9.4/boat-rider-transform.md`.
