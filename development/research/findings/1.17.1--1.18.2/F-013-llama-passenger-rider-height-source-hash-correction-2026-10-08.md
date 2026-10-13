# F-013: Llama lowers a Player passenger's vertical position in 1.18.2

1.17.1, mojmap: `1.17.1/mojmap/net/minecraft/world/entity/animal/horse/Llama.java`, `getPassengersRidingOffset`, lines 164–166.
1.18.2, mojmap: `1.18.2/mojmap/net/minecraft/world/entity/animal/horse/Llama.java`, `getPassengersRidingOffset`, lines 166–168.

Difference: For an equal Llama bounding-box height and the same passenger, 1.17.1 positions the player at the Llama's Y plus `height * 0.67` plus the passenger riding offset; 1.18.2 uses `height * 0.6` plus that same passenger offset. 1.18.2 therefore writes a player position lower by `height * 0.07` on each rider-position update. This is a direct player position result, not a claim about Llama acceleration, route or vehicle physics.

Movement meaning: For an equal Llama bounding-box height and the same passenger, 1.17.1 positions the player at the Llama's Y plus `height * 0.67` plus the passenger riding offset; 1.18.2 uses `height * 0.6` plus that same passenger offset. 1.18.2 therefore writes a player position lower by `height * 0.07` on each rider-position update.


## Current evidence and remaining work

Source decisions, implementation coverage and runtime parity are separate. Source paths are relative to `decompiled_minecraft/`; immutable Git evidence can be read with `git show <revision>:<path>`.

```json
{
  "implementation": {
    "status": "open",
    "changes": [],
    "hooks": [],
    "limits": [
      "Current annotated Change/interface/catalog inventory contains no operation implementing: F-013: Llama lowers a Player passenger's vertical position in 1.18.2. Existing unrelated later operations do not establish coverage."
    ]
  },
  "applicability": [
    "Endpoint interval (1.17.1, 1.18.2]; exact cutover remains the original evidence’s separate applicability gate."
  ],
  "sources": [
    {
      "version": "1.17.1",
      "mapping": "mojmap",
      "path": "1.17.1/mojmap/net/minecraft/world/entity/animal/horse/Llama.java",
      "member": "getPassengersRidingOffset",
      "lines": [
        [
          164,
          166
        ]
      ],
      "status": "different",
      "sha256": "0b41ea3feac6b413976171f45fca83fad0eb9e24f06de5f3ece40d20bfb04d72",
      "detail": "Canonical source read. Exact canonical file digest is absent/different in original: snippet content checked, original publication equivalence not asserted."
    },
    {
      "version": "1.18.2",
      "mapping": "mojmap",
      "path": "1.18.2/mojmap/net/minecraft/world/entity/animal/horse/Llama.java",
      "member": "getPassengersRidingOffset",
      "lines": [
        [
          166,
          168
        ]
      ],
      "status": "verified-content",
      "sha256": "48adc67e9e4746ae08ef77e813792c192a7a69ce76e499e87b4501bd658309de",
      "detail": "Canonical source read. Matches digest in archived original."
    }
  ],
  "sourceReviews": [
    {
      "verdict": "REQUEST CHANGES",
      "assertion": " The behavioral claim is supported: A `Llama.getPassengersRidingOffset` returns `getBbHeight() * 0.67`, B returns `getBbHeight() * 0.6`; `Llama.positionRider` applies the offset to rider position. The player path is reachable via tamed, grown llama interaction (`AbstractChestedHorse.mobInteract` / `doPlayerRide`), then `Player.startRiding` and the entity ride tick. Llamas and player riding exist in both versions. However, the finding's cited `AbstractChestedHorse.java` raw hashes do not identify the canonical source bytes, despite the files themselves matching each version's checked source-manifest row:",
      "assertion_preserved": false,
      "detail": "Exact original snapshot verdict; current compact wording does not renew acceptance. Later identity-only revision, where present, remains separate.",
      "snapshot": "e531086eecc778432b40b2b9ef39499b8a7f0dba:workflows/source-campaign-2026-10-07/1.17.1--1.18.2/findings/F-013-llama-passenger-rider-height-source-hash-correction-2026-10-08.md",
      "snapshot_sha256": "fc39381d46aa3257077dd20fd8ce7f8242b201b6b7c74ca9fc7fd2b1dceb511d",
      "identity": "4a5eae32b5819418dbd59eb4a763ee24ea1f2c52:development/research/archive/legacy-workflows/source-boundary-reviews/2026-10-08-mc1171-1182-snapshot-review.md"
    },
    {
      "verdict": "ACCEPT, identity-only correction",
      "assertion": "Only two producer source hashes corrected; source behavior and original other 13 verdicts unchanged. Original REQUEST CHANGES retained.",
      "assertion_preserved": true,
      "snapshot": "d7b09d64445c8d4700e1ad76b4d7b95cd0a33ca6",
      "snapshot_sha256": "1fbd87afa13a28150fef0cb6acfaa1d06751883b00b319e7d56e7bebc9263bbc",
      "identity": "4a5eae32b5819418dbd59eb4a763ee24ea1f2c52:development/research/archive/legacy-workflows/source-boundary-reviews/2026-10-08-mc1171-1182-snapshot-review.md"
    },
    {
      "verdict": "REQUEST CHANGES",
      "assertion": " The behavioral claim is supported: A `Llama.getPassengersRidingOffset` returns `getBbHeight() * 0.67`, B returns `getBbHeight() * 0.6`; `Llama.positionRider` applies the offset to rider position. The player path is reachable via tamed, grown llama interaction (`AbstractChestedHorse.mobInteract` / `doPlayerRide`), then `Player.startRiding` and the entity ride tick. Llamas and player riding exist in both versions. However, the finding's cited `AbstractChestedHorse.java` raw hashes do not identify the canonical source bytes, despite the files themselves matching each version's checked source-manifest row:",
      "assertion_preserved": true,
      "detail": "Exact original snapshot verdict; current compact wording does not renew acceptance. Later identity-only revision, where present, remains separate.",
      "snapshot": "e531086eecc778432b40b2b9ef39499b8a7f0dba:workflows/source-campaign-2026-10-07/1.17.1--1.18.2/findings/F-013-llama-passenger-rider-height.md",
      "snapshot_sha256": "fc39381d46aa3257077dd20fd8ce7f8242b201b6b7c74ca9fc7fd2b1dceb511d",
      "identity": "4a5eae32b5819418dbd59eb4a763ee24ea1f2c52:development/research/archive/legacy-workflows/source-boundary-reviews/2026-10-08-mc1171-1182-snapshot-review.md"
    }
  ],
  "openIssues": [
    {
      "missing": "Original digest binding for 1.17.1/mojmap/net/minecraft/world/entity/animal/horse/Llama.java is absent/different; canonical digest 0b41ea3feac6b413976171f45fca83fad0eb9e24f06de5f3ece40d20bfb04d72 was inspected.",
      "next_action": "Resolve against the cited archived publication/source manifest; do not assert byte-equivalence from this content check."
    }
  ],
  "runtime": "unverified"
}
```
