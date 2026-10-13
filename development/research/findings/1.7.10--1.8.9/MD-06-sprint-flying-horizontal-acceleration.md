# MD-06-sprint-flying-horizontal-acceleration

The older 1.7.10 canonical source directory is absent; historical claimed movement assertion is retained centrally without pretending paired source verification.

## Needed evidence

- Canonical exact 1.7.10 source and mapping publication Next: Source owner must publish/identify existing exact-version artifact in a separately authorized phase; then inspect the claimed older member.

```json
{
  "sources": [
    {
      "version": "1.7.10",
      "mapping": "ornithe-feather",
      "path": "1.7.10/ornithe-feather/net/minecraft/entity/living/player/PlayerEntity.java",
      "member": "PlayerEntity#moveRelative",
      "lines": null,
      "status": "missing",
      "detail": "Canonical version/file unavailable; historical symbol retained only as lookup target."
    },
    {
      "version": "1.8.9",
      "mapping": "ornithe-feather; feather-gen2-1.8.9+build.2-mergedv2.jar, feather-gen2-1.8.9+build.2.tiny",
      "path": "1.8.9/ornithe-feather/net/minecraft/entity/living/player/PlayerEntity.java",
      "member": "public void moveRelative(float sideways, float forwards) {",
      "lines": "1279-1295",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 e66cb294fc93118148a444bbafdf4dd57cbf66a23d69b1e8892cefccc690ab88; matches an archived source-file digest. Checked full declaration/body; snippet SHA-256 e49e02b0c1c173b167851f5b4faf1aa45f5a995e4a6e7932fb423a903a916b8a."
    }
  ],
  "sourceReviews": [],
  "implementation": "not admitted",
  "runtime": "unverified"
}
```
