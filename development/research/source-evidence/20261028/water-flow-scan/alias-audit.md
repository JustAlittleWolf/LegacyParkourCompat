# Independent audit: liquid scan alias closure, revision 2

**Verdict: ACCEPT** for the bounded liquid-scan alias closure in the exact report revision identified below. This acceptance supersedes the prior `REVISE` verdict for revision 1 only; it does not expand the audited scope.

## Bound report revision

- Report: `.native-source/20261028/liquid-scan-191-193-closure/comparison-revision-2.md`
- SHA-256: `03a5baf4de41e7a12e3f59c9bf12372551825447761bdb006ac8cf1661778ef1` (matches the assigned expected SHA-256).
- Diff against revision 1: the sole change is removal of the final “Reference integrity note” that incorrectly claimed the table entry was mistyped. The revision-2 table’s 1.9.4 `PlayerEntity.java` entry is the actual hash: `d658a0d95452d12bb7e347bfd802240eeecaf7f938e10dcd43e2640434387f85`.

## Rebound source evidence

Rechecked the canonical read-only source tree at `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft` for releases 1.9, 1.9.1, 1.9.2, 1.9.3 and 1.9.4. Revision 2 preserves the source claims and evidence from the audited revision; selected source-file hashes still match their release provenance maps for 1.9–1.9.3 and the source manifest for 1.9.4. The 1.9.4 `PlayerEntity.java` file and manifest entry both equal the table hash above.

The four hidden provenance record hashes for 1.9–1.9.3 remain respectively `8daacb7af8f210e716ee4b34c43b8a5d6f6b4b19831d2fd725cbe16ecf330a1a`, `e9fb0da6511597e2de953a143f9570b82243e5b9504603686e557f06271da64a`, `9928b467deb35c7e7f891b4fee1627a68feecdf233c2076909fd4834b0380510`, and `43325cdd53ca34b88ee9af8464782b323a948b98c8a556c4761f1e5737b84c3a`; the recorded Feather mapping, metadata and client input pins agree with the report. For 1.9.4, the provenance, ready, verification, source-manifest and artifact-manifest hashes all match the report. The ready marker’s source/artifact manifest and client pins and the artifact manifest’s mapping JAR and `.tiny` pins agree with the report.

Across all five exact release trees, the extracted method hashes match the report:

- `World.applyLiquidDrag(Box, Material, Entity)`: `cc35bea0df5e3aad83235368cbc07431f4f1470c6d319a2281609b81ef210e24`.
- `World.containsMaterial(Box, Material)`: `32aa474acfd430eed4837d7db5d0b33de1ba58500e1ecaea430521b39674f34c`.
- `World.isAreaLoaded(int, int, int, int, int, int, boolean)`: `affb2342bd8ae3ae43c03c6eb7712372bc2d2c9fc933f2528df0b5d366f839ea`.
- `Entity.checkWaterCollisions`: `bb008e529fb656f40bc3d62647da8bed0a02acc8a124463eb4ed9baa7217517f`.
- `Entity.isInLava`: `e0682c9491745ff612d6cb4b5b73be2fc9e43ba4ceae57f5d7b042b90500e242`.
- `LivingEntity.checkFallDamage`: `db2f4a6fa94bf3a4e059e408a4e1e948a078312c75359a288eb075f3daa07dc4`.
- `LivingEntity.moveRelative`: `8935df8b81194575e97189f46e7e59422988118ba73b1eca880c8340699359dd`.

The method bodies confirm the reported `floor(min*)` / `ceil(max*)` half-open scan bounds, scan loops and material predicate, loaded-area/height conditions and drag application; the ordinary-player water/lava callers and listed gates remain identical. Per-release literal call-site scans found the reported `Entity`, `ExperienceOrbEntity` and `ItemEntity` calls only.

## Boundary

Acceptance covers the report’s bounded 1.9–1.9.4 liquid-scan alias closure and source pins. It does not claim full movement-tick inventory or pair freeze, implementation correctness, runtime parity, or gameplay behavior. No build, tests or runtime launch was performed.
