package com.kharagedition.englishtibetandictionary.ui

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.DecelerateInterpolator
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import androidx.navigation.fragment.findNavController
import com.google.android.material.card.MaterialCardView
import com.kharagedition.englishtibetandictionary.R
import com.kharagedition.englishtibetandictionary.util.BottomSheetDialog


class HomeFragment : Fragment() {
    lateinit var topAnimation : Animation;
    lateinit var layout: LinearLayout;
    lateinit var settingCardView: MaterialCardView;
    private lateinit var favouriteCardView: MaterialCardView;
    lateinit var dictionrayCardView: MaterialCardView;
    private lateinit var settingIcon: ImageView;
    lateinit var favouriteIcon: ImageView;
    lateinit var dictionaryIcon: ImageView;
    private lateinit var rotation: Animation;
    private lateinit var pulseAnimation: ObjectAnimator;
    private lateinit var flipFromAnimation: ObjectAnimator;
    private lateinit var flipToAnimation: ObjectAnimator;
    lateinit var exitAppIcon: ImageView;


    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view =  inflater.inflate(R.layout.fragment_home, container, false)

        initView(view);
        initAnimation();

        initListener();
        topAnimation = AnimationUtils.loadAnimation(context, R.anim.layout_top_anim)
        layout.startAnimation(topAnimation);
        //rotate anmation
         rotation = AnimationUtils.loadAnimation(context, R.anim.button_rotate);

        return view;
    }

    private fun initAnimation() {
        //pulse animation
        pulseAnimation = ObjectAnimator.ofPropertyValuesHolder(
                favouriteIcon,
                PropertyValuesHolder.ofFloat("scaleX", 1.2f),
                PropertyValuesHolder.ofFloat("scaleY", 1.2f))
        pulseAnimation.duration = 300
        pulseAnimation.interpolator = FastOutSlowInInterpolator()
        pulseAnimation.repeatMode = ObjectAnimator.REVERSE

        //favourite animation
         flipFromAnimation = ObjectAnimator.ofFloat(dictionaryIcon, "scaleX", 1f, 0f)
         flipToAnimation = ObjectAnimator.ofFloat(dictionaryIcon, "scaleX", 0f, 1f)
        flipFromAnimation.duration = 150;
        flipToAnimation.duration = 150;
        flipFromAnimation.interpolator = DecelerateInterpolator()
         flipToAnimation.interpolator = AccelerateDecelerateInterpolator()
    }

    private fun initListener() {
        exitAppIcon.setOnClickListener {
            requireActivity().finishAffinity();
           /* val sheet =  BottomSheetDialog();
            sheet.show(requireActivity().supportFragmentManager,"ModalBottomSheet");*/
        };
        // DICTIONARY CARD  ONCLICK LISTENER
        dictionrayCardView.setOnClickListener {

            flipFromAnimation.start()

            flipFromAnimation.addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    super.onAnimationEnd(animation)
                    dictionaryIcon.setImageResource(R.drawable.ic_baseline_menu_book_24)
                    flipToAnimation.start()
                }
            })
            flipToAnimation.addListener(object : AnimatorListenerAdapter(){
                override fun onAnimationEnd(animation: Animator?) {
                    super.onAnimationEnd(animation)
                    findNavController().navigate(R.id.listFragment)
                }
            })
            //
        }
        // SETTING CARD ONCLICK LISTENER
        settingCardView.setOnClickListener {
            settingIcon.startAnimation(rotation);
            rotation.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationStart(animation: Animation?) {
                    Log.d("TAG", "onAnimationStart: ")
                }
                override fun onAnimationEnd(animation: Animation?) {
                    val sheet =  BottomSheetDialog();
            sheet.show(requireActivity().supportFragmentManager,"ModalBottomSheet");
                }
                override fun onAnimationRepeat(animation: Animation?) {
                    Log.d("TAG", "onAnimationRepeat: ")
                }
            })
            //findNavController().navigate(R.id.listFragment)

        };
        // FAVOURITE CARD ONCLICK LISTENER
        favouriteCardView.setOnClickListener {
            pulseAnimation.start();
        }
        pulseAnimation.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animator: Animator) {}
            override fun onAnimationEnd(animator: Animator) {
                findNavController().navigate(R.id.listFragment)
            }

            override fun onAnimationCancel(animator: Animator) {}
            override fun onAnimationRepeat(animator: Animator) {}
        })
    }

    private fun initView(view: View) {
        layout = view.findViewById(R.id.linearLayout)
        dictionrayCardView = view.findViewById(R.id.dictionary_card_view)
        favouriteCardView = view.findViewById(R.id.favourite_card_view)
        settingCardView = view.findViewById(R.id.setting_card_view)
        dictionaryIcon = view.findViewById(R.id.dictionary_icon)
        favouriteIcon = view.findViewById(R.id.fav_icon)
        settingIcon = view.findViewById(R.id.icon_settings)
        exitAppIcon = view.findViewById(R.id.exit_app)


    }


}