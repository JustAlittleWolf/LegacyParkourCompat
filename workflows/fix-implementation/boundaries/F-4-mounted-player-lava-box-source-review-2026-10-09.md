# F-4 mounted-player lava-box source-boundary review

- Review result: bounded **ACCEPT**.
- Reviewer: independent source-only reviewer `/root/f4_boundary_review`; review completed 2026-10-09.
- Reviewed boundary memo: commit `2d2e099eb9b8a652f30fc392bc02124a0f246e53`, blob `a87717a2433f58be8efa60c72267e6e60cac5545`.
- Readiness publication: commit `13dcacb388906800c1e3448633381de69ef51846`.
- Scope: cited exact-source files only; reviewer did not inspect mod implementation or wiki lanes.
- Finding: cited source hashes match their source-manifest entries; 26.1 `Entity.java`, `EntityFluidInteraction.java`, and `AbstractBoat.java` are byte-identical in 26.1.1 and 26.1.2. 1.21.11's lava scan uses the deflated entity box; 26.1 clips the passenger interaction box at the boat's upper surface. The first stable-release cutover is therefore 26.1. No source-boundary gap was found.
- Limit: this accepts the first-stable-cutover conclusion for the bounded witness. It does not establish full pair discovery completion, implementation correctness, build success, or runtime parity.
