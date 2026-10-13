# MD-07-flight-vertical-input-rounding

The older 1.7.10 canonical source directory is absent; historical claimed movement assertion is retained centrally without pretending paired source verification.

## Needed evidence

- Canonical exact 1.7.10 source and mapping publication Next: Source owner must publish/identify existing exact-version artifact in a separately authorized phase; then inspect the claimed older member.

```json
{
  "sources": [
    {
      "version": "1.7.10",
      "mapping": "ornithe-feather",
      "path": "1.7.10/ornithe-feather/net/minecraft/client/entity/living/player/InputClientPlayerEntity.java",
      "member": "InputClientPlayerEntity#mobTick",
      "lines": null,
      "status": "missing",
      "detail": "Canonical version/file unavailable; historical symbol retained only as lookup target."
    },
    {
      "version": "1.7.10",
      "mapping": "ornithe-feather",
      "path": "1.7.10/ornithe-feather/LocalClientPlayerEntity.java",
      "member": "LocalClientPlayerEntity#mobTick",
      "lines": null,
      "status": "missing",
      "detail": "Canonical version/file unavailable; historical symbol retained only as lookup target."
    },
    {
      "version": "1.8.9",
      "mapping": "ornithe-feather",
      "path": "1.8.9/ornithe-feather/InputClientPlayerEntity.java",
      "member": "InputClientPlayerEntity#mobTick",
      "lines": null,
      "status": "missing",
      "detail": "Canonical version/file unavailable; historical symbol retained only as lookup target."
    },
    {
      "version": "1.8.9",
      "mapping": "ornithe-feather; feather-gen2-1.8.9+build.2-mergedv2.jar, feather-gen2-1.8.9+build.2.tiny",
      "path": "1.8.9/ornithe-feather/net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java",
      "member": "public void mobTick() {",
      "lines": "488-637",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 1762b116e6b06d682b7daaa0fc8cce39b0ff455b3db8cf79dab03ac74f6c4053; matches an archived source-file digest. Checked full declaration/body; snippet SHA-256 5020da8f88b411b2f146fe8b9ef3402dddb2a4600180c538a379b7418935c807."
    }
  ],
  "sourceReviews": [],
  "implementation": "not admitted",
  "runtime": "unverified"
}
```
