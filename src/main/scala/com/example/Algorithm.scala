package com.example

import java.util.UUID

import com.example.CampaignsData.data

object Algorithm {

  def bid(request: BidRequest): Option[BidResponse] = {
    var res = data.filter(camp => camp.targeting.targetedSiteIds.contains(request.site.id))

    var filterUsersCountries: Seq[Campaign] = Seq()
    if (request.user.isDefined && request.user.get.geo.isDefined && request.user.get.geo.get.country.isDefined) {
      filterUsersCountries = data.filter(camp => camp.country.equalsIgnoreCase(request.user.get.geo.get.country.get))
    }
    if (filterUsersCountries.nonEmpty) {
      res = res.intersect(filterUsersCountries)
    }

    val cmp = res.sortWith((l, r) => l.bid > r.bid).collectFirst(camp => camp)
    if (cmp.isDefined) {
      Option(BidResponse(UUID.randomUUID().toString, request.id, cmp.get.bid, Option(cmp.get.id.toString), cmp.get.banners.collectFirst(ba => ba)))
    } else {
      Option.empty
    }
  }

}
