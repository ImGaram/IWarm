package com.example.iwarm.view

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.iwarm.R
import com.example.iwarm.databinding.FragmentSuggestClothesBinding

class SuggestClothesFragment : Fragment() {
    private lateinit var binding: FragmentSuggestClothesBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentSuggestClothesBinding.inflate(layoutInflater)
        val temp = arguments?.getFloat("temp")
        setView(temp)

        return binding.root
    }

    @SuppressLint("SetTextI18n")
    private fun setView(temp: Float?) {
        val text = binding.clothesText
        val description = binding.clothesDescription
        val image = binding.clothesImage
        if (temp != null) {
            if (temp < 4.0) {
                text.text = "패딩\n두꺼운 코트\n목도리\n기모 제품"
                description.text = "4℃가 내려가는 날씨는 굉장히 추운 한겨울 날씨로 롱패딩은 필수 템인 날씨입니다!"
                image.setImageResource(R.drawable.clothes_image1_padded_jacket)
            } else if (temp > 28.0) {
                text.text = "민소매\n반팔\n반바지\n원피스"
                description.text = "진짜 한여름 날씨 시작에요! 매미가 울고 아지랑이가 피는 더운 계절이에요! 에어컨과 선풍기가 필수이며, " +
                        "여름휴가를 떠나는 날씨입니다. 많은 사람들이 계곡으로, 바다로 피서를 떠나고 시원한 수박이 갈증을 채워주는 더운 날씨입니다."
                image.setImageResource(R.drawable.clothes_image7_sleeveless)
            } else {
                when(temp) {
                    in 5.0..8.0 -> {
                        text.text = "코트, 가죽 재킷, 히트텍\n니트, 레깅스"
                        description.text = "기온이 약간 올라오긴 했지만 여전히 코끝이 시린 겨울 날씨예요." +
                                " 롱패딩은 탈출했지만 코트와 목도리 정도는 입어야 몸의 온도를 보호할 수 있겠죠!"
                        image.setImageResource(R.drawable.clothes_image2_coat)
                    }
                    in 9.0..16.0 -> {
                        text.text = "재킷, 트렌치코트, 야상\n가디건, 니트, 청바지, 스타킹"
                        description.text = "드디어 이제 봄 날씨 시작이라고 할 수 있는 온도에요." +
                                " 하지만 아침저녁으로는 추운 시이기 때문에 꼭 재킷이나 겉옷을 챙겨 다녀야 하는 날씨입니다!"
                        image.setImageResource(R.drawable.clothes_image3_jacket)
                    }
                    in 17.0..19.0 -> {
                        text.text = "얇은 니트\n맨투맨\n가디건\n청바지"
                        description.text = "이젠 겨울의 차가운 바람이 완전히 물러나고 차갑지만 따듯한 봄 냄새가 나는 날씨예요! " +
                                "산책과 피크닉을 하기 좋은 날씨에 선선한 가을 날씨도 이 온도에 포함된답니다! 입고 싶은 옷을 마음껏 입을 수 있는 날씨예요~"
                        image.setImageResource(R.drawable.clothes_image4_sweatshirt)
                    }
                    in 20.0..22.0 -> {
                        text.text = "얇은 가디건\n긴팔\n면바지\n청바지"
                        description.text = "낮에는 덥다는 소리가 나올 정도로 봄 끝, 가을 시작을 알리는 온도에요. " +
                                "아직까진 입고 싶은 옷을 입을 수 있는 날씨죠! 이젠 겉옷이 없어도 활동하기 좋은 날씨예요. " +
                                "점점 가벼워 지는 옷차림에 여름을 준비하는 날씨예요!"
                        image.setImageResource(R.drawable.clothes_image5_cardigan)
                    }
                    in 23.0..27.0 -> {
                        text.text = "반팔\n얇은 셔츠\n반바지\n면바지"
                        description.text = "이젠 정말 여름 시작을 알리는 날씨예요! 밤에도 시원할 정도로 날씨가 많이 따뜻해졌어요." +
                                " 낮에는 아이스크림을 절로 찾게 되는 더운 날씨예요. 하지만 아직까지는 야외활동을 하는 데에는 딱 좋을 날씨죠." +
                                " 보통 5월 말 ~6월 초, 9월 말 ~ 10월 초 날씨예요. 여행 가기에도 좋은 날씨입니다."
                        image.setImageResource(R.drawable.clothes_image6_short_sleeve)
                    }
                }
            }
        }
    }
}