# Input identity correction witness

**Correction type:** hash transcription only. The existing `verdict.md` is unchanged.

I rehashed the exact current input and the existing verdict bytes:

- Frozen source report, `c-glide-climb-1141-1142-alias-gate/report.md`: SHA-256 `3aa1987ec03db2c0acfef4b1a275e959d4d15074dbe73e8ac0c4c082bd6bf84c`.
- Existing independent verdict, `c-glide-climb-1141-1142-alias-gate-audit/verdict.md`: SHA-256 `702b93612d2e827c9a51f6d7c261be2745e2f8b45d972dcc17f06c154ebaeab8`.

The old verdict's embedded input digest has a transposition (`...0acfe4f1...`) and therefore does not bind the report bytes. The report bytes still hash to the exact value above; this is not a changed report or a changed finding.

## Reaffirmed bounded conclusion

The old verdict's scoped conclusion and source references remain valid for the unchanged report identity: in 1.14.1 and 1.14.2, an ordinary local player with an already-active synchronized fall-flying flag in dry travel takes the glide branch before ordinary climb cleanup. Both versions use the same strict pre-move `lv5.y > -0.5` gate for `fallDistance = 1.0F`; the ordinary ladder/vine `method_18801` cleanup is in the non-glide branch; and the subsequent `Entity.move`/`Entity.fall` bookkeeping is the same. This remains a bounded no-difference audit, not full-pair coverage or runtime parity. The identical preceding Slow Falling reset and its interaction with the strict gate remain as stated in the verdict.

References are against the exact unchanged Yarn source trees identified by the report and checked in the audit: `LivingEntity.java` (`isClimbing`, `travel`, `method_18801`, `initAi`, `isFallFlying`), `Entity.java` (`move`, `fall`), `PlayerEntity.java` (`travel`), and `Blocks.java` ladder/vine registrations. Their actual hashes matched the corresponding local provenance manifest entries; Yarn artifact and metadata bytes matched their local manifest pins. No source or verdict content was edited.

**Signed witness:** `/root/c_glide_climb_1141_1142_alias_gate_audit`, independent source auditor, 2026-11-18.