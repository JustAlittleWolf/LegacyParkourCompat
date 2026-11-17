# Narrow audit: 1.16.3 → 1.16.4 BlockBehaviour path binding

Verdict: **ACCEPT** for the citation-only r2 correction.

## Immutable inputs

- Prior report: `build/source-mid-patches-20261117/1.16.3--1.16.4/ordinary_player/coverage-r1.md` — SHA-256 `E4975E26CA15D0150E71837ABB6BC05C8EE637A9F5AB543C1013F7600CCCD7F8`.
- Corrected report: `build/source-mid-patches-20261117/1.16.3--1.16.4/ordinary_player/coverage-r2.md` — SHA-256 `0D233B8289E630CB03108953417919E186FE2C62E1B2391E4EC0812297ACF941`.
- Prior bounded technical/inventory audit: `build/source-mid-patches-20261117/1.16.3--1.16.4/ordinary_player/audit/verdict.md` — SHA-256 `134bc5c52f1f92f4543c94c0c7252838cb6e3c6e2b8a0d8e9e26ad359858c64b`.

The r2 payload's only substantive change is expanding the short table citation `BlockBehaviour.java` to `net/minecraft/world/level/block/state/BlockBehaviour.java`. It also contains one additional trailing blank line. No technical claim, source evidence, identity, applicability boundary, or dependency changed. The prior audit accepted the bounded technical and inventory claims and required only this citation correction; those unchanged checks are reused.

## Corrected exact source binding

The corrected path exists in both canonical Mojmap roots. `BlockBehaviour.java` SHA-256 `c62e2f07094e5a7bcad00cbae7d24497bf636cc740232a1472f4128a472def8b` matches the on-disk source and the `sources` entry in each exact mapping-local `.decompile-provenance.json`:

- 1.16.3 manifest/provenance: `decompiled_minecraft/1.16.3/mojmap/.decompile-provenance.json`, SHA-256 `513271804ead6757345d305717e7e0bfc1641434c35a3b254f287bd8fd281498`.
- 1.16.4 manifest/provenance: `decompiled_minecraft/1.16.4/mojmap/.decompile-provenance.json`, SHA-256 `1e04555c915527a398a2c7150679fb24d056f54bd680261e3c1dd55da821147c`.

## Scope retained

Acceptance is limited to the citation correction and reuses the prior bounded source comparison. Pair coverage remains PARTIAL; open dependencies, resources and runtime gates remain open. This does not freeze the pair.