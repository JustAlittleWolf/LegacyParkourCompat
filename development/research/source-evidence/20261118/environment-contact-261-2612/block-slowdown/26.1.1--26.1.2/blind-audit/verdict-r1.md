# Independent author review verdict — revision 1

**Reviewed author SHA-256:** `1f90c478f21f6726dacf016038aaadafa377197e912e96325836fe10729a7fc7` (verified).  
**Bound frozen audit SHA-256:** `17886d08b0cd14e4891ceb8927460c910efb41f3609ceef1f0660bfcf41703e6` (verified; preserved).  
**Prior review SHA-256:** `ed130d44428485bc363e211b5b8eb2d3beb7bf6a5ef5dd9e1b2997fa82147c5e` (verified; preserved).

## Verdict

Revision 1 corrects the substantive honey-velocity error, states the Player flight override and excludes abilities flight from the claimed stuck-speed path, and describes the 26.1.2 upstream-origin evidence gap. The bounded no-difference conclusion agrees with the frozen audit and remains properly limited to selected source paths; it does not claim shape/provider or runtime equivalence. No movement difference should be raised for this slice.

Two documentation residuals remain before the draft fully resolves the prior review:

1. **Equal input should include pre-contact entity state.** The draft holds contact geometry, support and “all relevant query values” equal, and excludes abilities flight, but does not explicitly hold the Player's pre-contact position, velocity and on-ground state equal. Honey's gate and horizontal branch read those values; the cobweb producer reads living/Weaving state. Add a short clause that the compared contact starts from equal relevant Player movement state and equal block/effect state as well as equal geometry/support/query results. The current no-difference claim is source-supported under that same-state interpretation, but that interpretation should be explicit.
2. **Explain the canonical-source link to the ready records.** The draft verifies each ready marker's source-manifest hash against the local manifest and reports matching source-file hashes, which supports local content identity. It omits that the ready/provenance records point at dated staging/output paths rather than the canonical `decompiled_minecraft` root; state that the canonical trees are linked to those records by their matching whole-source-manifest hashes. This prevents a reader from mistaking the recorded output path for the canonical source root.

The honey line range `HoneyBlock.java:89-105` still omits the final horizontal-overlap predicate; use `:89-109` for the full sliding gate. This is a citation completeness issue, not a disagreement with the source-equivalence result. The 26.1.2 local-pins caveat is otherwise adequate: its local content hashes are recorded, but the provenance object does not independently authenticate upstream origin.

This review is bound to the author, freeze and prior-verdict revisions named above. It does not amend or renew full-pair coverage or alter the frozen audit.