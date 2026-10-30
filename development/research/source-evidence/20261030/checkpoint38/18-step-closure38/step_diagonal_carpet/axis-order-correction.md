# Axis-order correction for diagonal carpet witness

This correction supplements `diagonal-carpet-witness.md`; that report is preserved byte-for-byte at SHA-256 `8384b9353b020a5a18d1756a9bf2d35eec6778aef292c9264bae5e9f3d65db15`.

The report mislabeled the second raised candidate. In both exact releases, **both candidate loops run Up → X → Z**. There is no Up → Z → X candidate in this `Entity.move` code.

- First candidate (1.8 `Entity.java` around 471–491; 1.8.1 around 476–496): upward clip `var33` against `var32 = startingBox.expanded(dx, 0, dz)`, then X via `var75`, then Z via `var76`.
- Second candidate (1.8 around 493–511; 1.8.1 around 498–516): upward clip `var81` against `var77 = startingBox`, then X via `var82`, then Z via `var83`.
- Route comparison is `var84 > var48`; equal horizontal distances choose the second candidate in the `else` branch (`Entity.java` 1.8 around 513–524; 1.8.1 around 518–529).
- With this geometry, candidate 1's expanded ascent box and candidate 2's starting ascent box both intersect the ceiling. The carpet is present in the reused raised-query list, but does not clip either ascent. Each rise is `.20`. At raised height the player clears carpet vertically, so X reaches `.25` then Z reaches `-.03` in **both** candidates. Both endpoints are `[.45,1.05]×[1.20,3.00]×[-.62,-.02]`; both horizontal distances squared are `.0634`. Thus the second candidate wins the tie, with `var81=.20`.
- Fallback still does not restore the ordinary route: normal progress squared is `.0409 < .0634`. In 1.8 the chosen candidate descends by `-.6` and ends at feet `y=.6`; in 1.8.1 the selected second route descends by `-var81=-.2` and ends at feet `y=1.0`. Carpet is Z-disjoint at descent and floor is absent from the raised query, so neither descent is clipped.

Accordingly, the route-order correction does **not** invalidate the witness or its ordinary-player reachability argument. It corrects only the second-candidate order and identifies the selected 1.8.1 ascent variable as `var81`.
