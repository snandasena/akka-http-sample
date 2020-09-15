package com.example

import java.util.UUID

import com.example.CampaignsData.data

object Algorithm {

  def bidOn(request: BidRequest): Option[BidResponse] = {
    val filteredCampaigns = data.filter(camp => filterBidRequest(request, camp))
      .filter(camp => filterBidFloor(request, camp))

    if (filteredCampaigns.nonEmpty) {
      var selectedCampaign = filteredCampaigns.collectFirst(camp => camp).get
      val filteredBannerCampaigns = filteredCampaigns.filter(camp => filterBanner(request, camp))
      if (filteredBannerCampaigns.nonEmpty) {
        selectedCampaign = filteredBannerCampaigns.collectFirst(cam => cam).get
        val selectedBanner = selectedCampaign.banners.collectFirst(banner => banner)
        Option(BidResponse(UUID.randomUUID().toString, request.id, selectedCampaign.bid, Option(selectedCampaign.id.toString), selectedBanner))
      } else {
        Option(BidResponse(UUID.randomUUID().toString, request.id, selectedCampaign.bid, Option.empty, Option.empty))
      }
    } else {
      Option.empty
    }
  }

  private def filterBidRequest(request: BidRequest, campaign: Campaign): Boolean = {
    filterTargetSites(request, campaign) || filterDeviceCountry(request, campaign) ||
      filterUserCountry(request, campaign)
  }

  private def filterTargetSites(request: BidRequest, campaign: Campaign): Boolean =
    campaign.targeting.targetedSiteIds.contains(request.site.id)

  private def filterUserCountry(request: BidRequest, campaign: Campaign): Boolean =
    request.user match {
      case None => true
      case Some(user) => user.geo match {
        case None => true
        case Some(geo) => geo.country match {
          case None => true
          case Some(country) => campaign.country.equalsIgnoreCase(country)
        }
      }
    }


  private def filterDeviceCountry(request: BidRequest, campaign: Campaign): Boolean =
    request.device match {
      case None => true
      case Some(device) => device.geo match {
        case None => true
        case Some(geo) => geo.country match {
          case None => true
          case Some(country) => campaign.country.equalsIgnoreCase(country)
        }
      }
    }

  private def filterBidFloor(request: BidRequest, campaign: Campaign): Boolean =
    request.imp match {
      case None => true
      case Some(impressions) => impressions.exists(imp => imp.bidFloor match {
        case None => true
        case Some(bid) => campaign.bid >= bid
      })
    }


  private def filterBanner(request: BidRequest, campaign: Campaign): Boolean = {
    request.imp match {
      case None => true
      case Some(impressions) => impressions.exists(imp => {
        campaign.banners.exists(banner => {
          val widthOk = imp.w.isDefined && banner.width == imp.w.get
          val heightOk = imp.h.isDefined && banner.height == imp.h.get
          val widthLowerBoundOk = imp.wmin.isDefined && banner.width >= imp.wmin.get
          val widthUpperBoundOk = imp.wmax.isDefined && banner.width <= imp.wmax.get
          val heightLowerBoundOk = imp.hmin.isDefined && banner.height >= imp.hmin.get
          val heightUpperBoundOk = imp.hmax.isDefined && banner.height <= imp.hmax.get
          (widthOk && heightOk) || (widthLowerBoundOk && heightLowerBoundOk) || (widthUpperBoundOk && heightUpperBoundOk)
        })
      })
    }
  }
}

