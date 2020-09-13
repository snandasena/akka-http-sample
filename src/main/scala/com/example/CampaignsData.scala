package com.example

object CampaignsData {
  val data: Seq[Campaign] = Seq(
    Campaign(1, "Sri Lanka", Targeting(Set(1, 2, 5)),
      List(
        Banner(1, "src1", 500, 400),
        Banner(2, "src2", 600, 400),
        Banner(3, "src3", 500, 300),
        Banner(4, "src4", 720, 90),
        Banner(5, "src5", 600, 600),
      ), 0.001),
    Campaign(2, "Lithuania", Targeting(Set(1, 3, 4, 5)),
      List(
        Banner(6, "src6", 500, 400),
        Banner(7, "src7", 500, 400),
        Banner(8, "src8", 500, 400),
        Banner(9, "src9", 500, 400),
        Banner(10, "src10", 500, 400),
      ), 0.01),
    Campaign(3, "Russia", Targeting(Set(1, 2, 3, 4)), List(Banner(11, "src12", 500, 400)), 0.1),
    Campaign(4, "Poland", Targeting(Set(4, 5)), List(Banner(12, "src12", 500, 400)), 0.02),
    Campaign(5, "German", Targeting(Set(1)), List(Banner(13, "src13", 500, 400)), 0.03),
    Campaign(6, "Sri Lanka", Targeting(Set(2, 3, 4)), List(Banner(14, "src14", 500, 400)), 0.4))
}
