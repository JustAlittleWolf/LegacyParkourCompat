# F-013 source-hash correction binding

Date: 2026-10-08
Purpose: identity-only correction for F-013, `Llama#getPassengersRidingOffset` / rider Y placement. This does not revise its bounded source conclusion, player reachability, or applicability. No pair coverage row or historical handoff was edited.

## Corrected immutable snapshot

- Snapshot commit: `d7b09d64445c8d4700e1ad76b4d7b95cd0a33ca6`
- Snapshot path: `workflows/source-campaign-2026-10-07/1.17.1--1.18.2/findings/F-013-llama-passenger-rider-height-source-hash-correction-2026-10-08.md`
- Git blob: `3d2e66195b4d2620cba264a77b598bfbbaea833b`
- Raw-file SHA-256: `1fbd87afa13a28150fef0cb6acfaa1d06751883b00b319e7d56e7bebc9263bbc`
- The corrected snapshot is a byte-for-byte copy of the prior candidate except for the two `AbstractChestedHorse.java` source hashes listed below.

## Exact source identity verification

Both ready markers report `status=ready`, exact version ID, and `mapping=mojmap`. Each marker's source-manifest SHA-256 matches the manifest file. The source-file manifest entry and direct file SHA-256 agree for each changed identity.

| Side | Ready source root | Source manifest SHA-256 | `AbstractChestedHorse.java` source SHA-256 |
|---|---|---|---|
| A, 1.17.1 | `build/movement-campaign-2026-10-07/ready/1.17.1/mojmap` | `93270d229acfb751bf56daf1e7be26ce3dcff26b29e94aa157de621405a3463b` | `b302997039f3b9b9db5115f796cdf1ad160eb271019420355f29098a196d5e50` |
| B, 1.18.2 | `build/movement-campaign-2026-10-07/ready/1.18.2/mojmap` | `aea0cb9c6fc8f7a46f0eb82b0388ad58a4659f513be6c0a2be06c0df0c1eb07a` | `47d22d9cc4455587211f0aa5881f8639e49250206e600a1a04d90174b5fb76a4` |

The prior snapshot had `AbstractChestedHorse.java` hashes `8ef7b1f59605daad9d17c401fe9992a46151bc6ae3bd17e5225d004edca359e1` (A) and `2ab94ae9c55dc5413d5a13f6cd2dcc432f97544fa6d9db6d07c793ce572ebbc9` (B). Those two identities alone were incorrect. Llama, Entity, all other source identities, and the finding text remain unchanged.

## Prior immutable snapshot retained

- Prior snapshot checkpoint: `cf7a878e5ea86e86f7ed9920d43caa7d665b62e8`
- Prior snapshot path: `workflows/source-campaign-2026-10-07/1.17.1--1.18.2/findings/F-013-llama-passenger-rider-height.md`
- Prior Git blob: `7f021ef954c2e90314bea14e0e9ed06bbc7c2ef1`
- Prior raw-file SHA-256: `fc39381d46aa3257077dd20fd8ce7f8242b201b6b7c74ca9fc7fd2b1dceb511d`

The existing `SOURCE-HANDOFF-2026-10-08.md` is preserved unchanged. This memo and the corrected snapshot provide a distinct rereview target; reviewer disposition must be updated only by the authorized independent reviewer.
